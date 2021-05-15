package com.zeepy.server.controller;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;

@WebMvcTest
public class ReviewControllerTest extends ControllerTest {

    @MockBean
    ReviewService reviewService;

    @BeforeEach
    @Override
    public void setUp(WebApplicationContext webApplicationContext) {
        super.setUp(webApplicationContext);
    }

    @Test
    @DisplayName("리뷰 삭제 기능 테스트")
    public void deleteReviewTest() throws Exception {
        doNothing().when(reviewService).deleteReview(1L);
        doDelete("/api/review/1");
    }
}
