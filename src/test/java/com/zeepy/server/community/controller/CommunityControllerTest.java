package com.zeepy.server.community.controller;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.dto.LikeRequestDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.service.CommunityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

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
        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .communityId(1L)
                .userId(1L)
                .build();
        given(communityService.like(any(LikeRequestDto.class))).willReturn(1L);
        doPost("/api/community/like", likeRequestDto);
    }

    @DisplayName("좋아요_취소_테스트")
    @Test
    public void cancelLike() throws Exception {
        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .userId(1L)
                .communityId(1L)
                .build();
        doNothing().when(communityService).cancelLike(likeRequestDto);
        doDelete("/api/community/like-cancel", likeRequestDto);
    }
}
