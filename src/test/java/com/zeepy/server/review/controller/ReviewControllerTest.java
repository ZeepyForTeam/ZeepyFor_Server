package com.zeepy.server.review.controller;

import static org.mockito.BDDMockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.Furniture;
import com.zeepy.server.review.domain.LessorAge;
import com.zeepy.server.review.domain.LessorGender;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.domain.RoomCount;
import com.zeepy.server.review.domain.TotalEvaluation;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.service.ReviewService;

@DisplayName("ReviewController_테스트_클래스")
@WebMvcTest(controllers = {ReviewController.class}, includeFilters = @ComponentScan.Filter(classes = {
    EnableWebSecurity.class}))
@MockBean(JpaMetamodelMappingContext.class)
public class ReviewControllerTest extends ControllerTest {
    @MockBean
    private ReviewService reviewService;

    @BeforeEach
    @Override
    public void setUp(WebApplicationContext webApplicationContext) {
        super.setUp(webApplicationContext);
    }

    @Test
    @DisplayName("리뷰 조회 기능 테스트")
    public void getReview() throws Exception {
        doGet("/api/review/hello");
    }

    @Test
    @DisplayName("리뷰 생성 기능 테스트")
    public void saveReview() throws Exception {
        given(reviewService.create(any())).willReturn(1L);
        ReviewDto review = ReviewDto.builder()
            .address("주소")
            .communcationTendency(CommuncationTendency.BUSINESS.name())
            .lessorGender(LessorGender.MALE.name())
            .lessorAge(LessorAge.FIFTY.name())
            .lessorReview("집주인 리뷰")
            .roomCount(RoomCount.ONE.name())
            .soundInsulation(MultiChoiceReview.GOOD.name())
            .pest(MultiChoiceReview.GOOD.name())
            .lightning(MultiChoiceReview.PROPER.name())
            .waterPressure(MultiChoiceReview.GOOD.name())
            .furnitures(Collections.singletonList(Furniture.AIRCONDITIONAL.name()))
            .review("리뷰")
            .totalEvaluation(TotalEvaluation.GOOD.name())
            .buildingId(1L)
            .build();
        doPost("/api/review", review);
    }

    @Test
    @DisplayName("리뷰 삭제 기능 테스트")
    public void deleteReviewTest() throws Exception {
        doNothing().when(reviewService).deleteReview(1L);
        doDelete("/api/review/1");
    }
}