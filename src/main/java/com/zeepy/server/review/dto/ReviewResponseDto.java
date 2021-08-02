package com.zeepy.server.review.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.Furniture;
import com.zeepy.server.review.domain.LessorAge;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.domain.Review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResponseDto {
    private Long id;
    private Long user;
    private String address;
    private LessorAge lessorAge;
    private CommuncationTendency communcationTendency;
    private MultiChoiceReview soundInsulation;
    private MultiChoiceReview pest;
    private MultiChoiceReview lightning;
    private MultiChoiceReview waterPressure;
    private String lessorReview;
    private List<Furniture> furnitures;
    private String review;
    private List<String> imageUrls;

    public ReviewResponseDto(
        Long id,
        Long user,
        String address,
        LessorAge lessorAge,
        CommuncationTendency communcationTendency,
        MultiChoiceReview soundInsulation,
        MultiChoiceReview pest,
        MultiChoiceReview lightning,
        MultiChoiceReview waterPressure,
        String lessorReview,
        List<Furniture> furnitures,
        String review,
        List<String> imageUrls
    ) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.lessorAge = lessorAge;
        this.communcationTendency = communcationTendency;
        this.soundInsulation = soundInsulation;
        this.pest = pest;
        this.lightning = lightning;
        this.waterPressure = waterPressure;
        this.lessorReview = lessorReview;
        this.furnitures = furnitures;
        this.review = review;
        this.imageUrls = imageUrls;
    }

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.user = review.getUser();
        this.address = review.getAddress();
        this.lessorAge = review.getLessorAge();
        this.communcationTendency = review.getCommunicationTendency();
        this.soundInsulation = review.getSoundInsulation();
        this.pest = review.getPest();
        this.lightning = review.getLightning();
        this.waterPressure = review.getWaterPressure();
        this.lessorReview = review.getLessorReview();
        this.furnitures = review.getFurnitures();
        this.review = review.getReview();
        this.imageUrls = review.getImageUrls();
    }

    public static ReviewResponseDto of(Review review) {
        return new ReviewResponseDto(
            review.getId(),
            review.getUser(),
            review.getAddress(),
            review.getLessorAge(),
            review.getCommunicationTendency(),
            review.getSoundInsulation(),
            review.getPest(),
            review.getLightning(),
            review.getWaterPressure(),
            review.getLessorReview(),
            review.getFurnitures(),
            review.getReview(),
            review.getImageUrls()
        );
    }

    public static List<ReviewResponseDto> listOf(List<Review> reviewList) {
        return reviewList
            .stream()
            .map(ReviewResponseDto::of)
            .collect(Collectors.toList());
    }
}
