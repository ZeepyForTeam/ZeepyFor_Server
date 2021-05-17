package com.zeepy.server.review.controller;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.review.domain.*;
import com.zeepy.server.review.dto.ReviewDto;
import com.zeepy.server.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

@DisplayName("ReviewController_테스트_클래스")
@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest extends ControllerTest {

    @Override
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        super.setUp(webApplicationContext);
    }

    @DisplayName("Review_생성_테스트")
    @Test
    public void saveTest() throws Exception {
        ReviewDto requestDto = new ReviewDto(1L,
                "sssss",
                CommuncationTendency.BUSINESS,
                LessorGender.MALE,
                LessorAge.FOURTY,
                "aaaaa",
                RoomCount.THREEORMORE,
                MultiChoiceReview.GOOD,
                MultiChoiceReview.GOOD,
                MultiChoiceReview.GOOD,
                MultiChoiceReview.GOOD,
                Arrays.asList(Furniture.AIRCONDITIONAL,Furniture.AIRCONDITIONAL),
                "asda",
                TotalEvaluation.GOOD,
                Arrays.asList("1","2","3")
                );
        doPost("/api/review",requestDto);
    }

    @Test
    @DisplayName("ReviewList_불러오기_테스트")
    public void getReviewListTest() throws Exception{
        ReviewDto requestDto = new ReviewDto(1L,
                "sssss",
                CommuncationTendency.BUSINESS,
                LessorGender.MALE,
                LessorAge.FOURTY,
                "aaaaa",
                RoomCount.THREEORMORE,
                MultiChoiceReview.GOOD,
                MultiChoiceReview.GOOD,
                MultiChoiceReview.GOOD,
                MultiChoiceReview.GOOD,
                Arrays.asList(Furniture.AIRCONDITIONAL,Furniture.AIRCONDITIONAL),
                "asda",
                TotalEvaluation.GOOD,
                Arrays.asList("1","2","3")
        );
        String path = "/api/review/1";
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

        doGet(path,params);


    }
}
