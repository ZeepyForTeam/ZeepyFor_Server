package com.zeepy.server.community.controller;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
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
}
