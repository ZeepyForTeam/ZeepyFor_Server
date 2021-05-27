package com.zeepy.server.community.controller;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.*;
import com.zeepy.server.community.service.CommunityService;
import com.zeepy.server.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@DisplayName("커뮤니티_컨트롤러_테스트")
@WebMvcTest(controllers = CommunityController.class)
public class CommunityControllerTest extends ControllerTest {

    @MockBean
    private CommunityService communityService;

    @Override
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        super.setUp(webApplicationContext);
    }

    @DisplayName("커뮤니티_등록_테스트")
    @Test
    public void save() throws Exception {
        SaveCommunityRequestDto requestDto = SaveCommunityRequestDto.builder()
                .communityCategory(CommunityCategory.FREESHARING)
                .productName("인프런 springboot 강의")
                .productPrice(80000)
                .sharingMethod("카카오톡")
                .targetNumberOfPeople(3)
                .targetAmount(null)
                .title("강의 공동 구매해요!")
                .content("제곧내")
                .imageUrls(Arrays.asList("asdasd", "aaaaaaa", "ccccccccc"))
                .build();

        given(communityService.save(any(SaveCommunityRequestDto.class))).willReturn(1L);

        doPost("/api/community", requestDto);
    }

    @DisplayName("참가하기_테스트")
    @Test
    public void joinCommunity() throws Exception {
        //given
        long communityId = 1L;
        long joinUserId = 2L;
        JoinCommunityRequestDto requestDto = new JoinCommunityRequestDto(null, joinUserId);

        given(communityService.joinCommunity(any(Long.class), any(JoinCommunityRequestDto.class))).willReturn(1L);

        //when
        //then
        doPost("/api/community/participation/" + communityId, requestDto);
    }

    @DisplayName("나의ZIP참여목록_테스트")
    @Test
    public void testGetMyZipJoinList() throws Exception {
        //given
        long joinUserId = 2L;
        User writerUser = User.builder().id(1L).name("작성자").build();
        User writerUser2 = User.builder().id(3L).name("작성자2").build();
        User joinUser = User.builder().id(2L).name("참여자").build();
        Community writeCommunity = Community.builder().id(1L).communityCategory(CommunityCategory.NEIGHBORHOODFRIEND).title("제목1").content("내용").user(writerUser).build();
        Community otherCommunity = Community.builder().id(2L).communityCategory(CommunityCategory.NEIGHBORHOODFRIEND).title("제목2").content("내용2").user(writerUser2).build();
        Participation participation = Participation.builder().id(1L).community(otherCommunity).user(joinUser).build();
        ParticipationResDto participationResDto = new ParticipationResDto(participation);
        WriteOutResDto writeOutResDto = new WriteOutResDto(writeCommunity);

        List<ParticipationResDto> participationResDtoList = new ArrayList<>();
        participationResDtoList.add(participationResDto);

        List<WriteOutResDto> writeOutResDtoList = new ArrayList<>();
        writeOutResDtoList.add(writeOutResDto);

        MyZipJoinResDto resultResDto = new MyZipJoinResDto(participationResDtoList, writeOutResDtoList);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        given(communityService.getJoinList(joinUserId)).willReturn(resultResDto);

        //when
        //then
        doGet("/api/community/participation/1", params);
    }

    @DisplayName("참여취소하기")
    @Test
    public void cancelParticipation() throws Exception {
        //given
        long communityId = 1L;
        String url = "/api/community/participation/" + communityId;
        long userId = 2L;

        CancelJoinCommunityRequestDto requestDto = new CancelJoinCommunityRequestDto(userId);
        doNothing().when(communityService).cancelJoinCommunity(communityId, requestDto);

        //when
        //then
        doPut(url, requestDto);
    }

    @DisplayName("댓글작성하기")
    @Test
    public void setComment() throws Exception {
        //given
        long communityId = 1L;
        String url = "/api/community/comment/" + communityId;

        User commentUser = User.builder().id(2L).name("댓글작성자").build();

        WriteCommentRequestDto requestDto = new WriteCommentRequestDto("댓글1", null, commentUser.getId());
        doNothing().when(communityService).saveComment(communityId, requestDto);

        //when
        //then
        doPost(url, requestDto);

    }
}
