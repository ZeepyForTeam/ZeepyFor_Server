package com.zeepy.server.community.controller;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.dto.CommunityLikeRequestDto;
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunityResponseDtos;
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

    @DisplayName("좋아요_추가_테스트")
    @Test
    public void like() throws Exception {
        CommunityLikeRequestDto communityLikeRequestDto = CommunityLikeRequestDto.builder()
                .communityId(1L)
                .userId(1L)
                .build();
        given(communityService.like(any(CommunityLikeRequestDto.class))).willReturn(1L);
        doPost("/api/community/like", communityLikeRequestDto);
    }

    @DisplayName("좋아요_취소_테스트")
    @Test
    public void cancelLike() throws Exception {
        CommunityLikeRequestDto communityLikeRequestDto = CommunityLikeRequestDto.builder()
                .userId(1L)
                .communityId(1L)
                .build();
        doNothing().when(communityService).cancelLike(communityLikeRequestDto);
        doDelete("/api/community/like-cancel", communityLikeRequestDto);
    }

    @DisplayName("좋아요_누른_커뮤니티_불러오기_테스트")
    @Test
    public void getLikeList() throws Exception {
        List<CommunityResponseDto> communityResponseDtoList = new ArrayList<CommunityResponseDto>();
        CommunityResponseDtos communityResponseDtos = new CommunityResponseDtos(communityResponseDtoList);
        given(communityService.getLikeList(any(Long.class))).willReturn(communityResponseDtos);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        doGet("/api/community/likes", params);
    }
}
