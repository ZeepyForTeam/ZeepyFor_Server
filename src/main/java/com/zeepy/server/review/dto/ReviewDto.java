package com.zeepy.server.review.dto;

import com.zeepy.server.review.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ReviewDto {
    private Long user; // 변경 될 겁니다.
    private String address;
    private CommuncationTendency communcationTendency;
    private LessorGender lessorGender;
    private LessorAge lessorAge;
    private String lessorReview;
    private RoomCount roomCount;
    private MultiChoiceReview soundInsulation;
    private MultiChoiceReview pest;
    private MultiChoiceReview lightning;
    private MultiChoiceReview waterPressure;
    private List<Furniture> furnitures;
    private String review;
    private TotalEvaluation totalEvaluation;
    private List<String> imageUrls;

    public ReviewDto(
            Long user, String address,
            CommuncationTendency communcationTendency,
            LessorGender lessorGender,
            LessorAge lessorAge,
            String lessorReview,
            RoomCount roomCount,
            MultiChoiceReview soundInsulation,
            MultiChoiceReview pest,
            MultiChoiceReview lightning,
            MultiChoiceReview waterPressure,
            List<Furniture> furnitures,
            String review,
            TotalEvaluation totalEvaluation,
            List<String> imageUrls
    ) {
        this.user = user;
        this.address = address;
        this.communcationTendency = communcationTendency;
        this.lessorGender = lessorGender;
        this.lessorAge = lessorAge;
        this.lessorReview = lessorReview;
        this.roomCount = roomCount;
        this.soundInsulation = soundInsulation;
        this.pest = pest;
        this.lightning = lightning;
        this.waterPressure = waterPressure;
        this.furnitures = furnitures;
        this.review = review;
        this.totalEvaluation = totalEvaluation;
        this.imageUrls = imageUrls;
    }

    public Review returnReviewEntity() {
        return new Review(null,
                this.user,
                this.address,
                this.communcationTendency,
                this.lessorGender,
                this.lessorAge,
                this.lessorReview,
                this.roomCount,
                this.soundInsulation,
                this.pest,
                this.lightning,
                this.waterPressure,
                this.furnitures,
                this.review,
                this.totalEvaluation,
                this.imageUrls
        );
    }
}
