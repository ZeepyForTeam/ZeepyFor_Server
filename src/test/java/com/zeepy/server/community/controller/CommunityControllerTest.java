package com.zeepy.server.community.controller;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.ParticipationResDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
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
        doPost("/api/community/" + communityId, requestDto);
    }

    @DisplayName("나의ZIP참여목록_테스트")
    @Test
    public void testGetMyZipJoinList() throws Exception {
        //given
        long joinUserId = 2L;
        User writerUser = User.builder().id(1L).name("작성자").build();
        User joinUser = User.builder().id(2L).name("참여자").build();
        Community community = Community.builder().id(1L).communityCategory(CommunityCategory.NEIGHBORHOODFRIEND).title("제목1").content("내용").user(writerUser).build();

        Participation participation = Participation.builder().id(1L).community(community).user(joinUser).build();
        ParticipationResDto resDto = new ParticipationResDto(participation);

        List<ParticipationResDto> joinList = new ArrayList<>();
        joinList.add(resDto);

        given(communityService.getJoinList(joinUserId)).willReturn(joinList);

        //when
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        //then
        doGet("/api/community/participation/1", params);
    }
}
