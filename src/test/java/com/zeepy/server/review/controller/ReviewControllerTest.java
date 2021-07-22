package com.zeepy.server.review.controller;

import static org.mockito.BDDMockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.common.config.security.CustomAccessDeniedHandler;
import com.zeepy.server.common.config.security.CustomAuthenticationEntryPoint;
import com.zeepy.server.common.config.security.JwtAuthenticationProvider;
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
public class ReviewControllerTest extends ControllerTest {

	@MockBean
	private ReviewService reviewService;
	@MockBean
	JwtAuthenticationProvider jwtAuthenticationProvider;
	@MockBean
	CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@MockBean
	CustomAccessDeniedHandler customAccessDeniedHandler;

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
			Arrays.asList(Furniture.AIRCONDITIONAL, Furniture.AIRCONDITIONAL),
			"asda",
			TotalEvaluation.GOOD,
			Arrays.asList("1", "2", "3")
		);
		given(reviewService.create(any())).willReturn(1L);
		doPost("/api/review", requestDto);
	}

	@Test
	@DisplayName("ReviewList_불러오기_테스트")
	public void getReviewListTest() throws Exception {
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
			Arrays.asList(Furniture.AIRCONDITIONAL, Furniture.AIRCONDITIONAL),
			"asda",
			TotalEvaluation.GOOD,
			Arrays.asList("1", "2", "3")
		);
		String path = "/api/review/1";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		doGet(path, params);

	}
}
