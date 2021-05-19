package com.zeepy.server.review.dto;

import com.zeepy.server.review.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ReviewDto {

    private Long user; // 변경 될 겁니다.

    @NotBlank(message = "주소는 필수값입니다.")
    private String address;

    @NotBlank(message = "소통경향은 필수값입니다.")
    private CommuncationTendency communcationTendency;

    @NotBlank(message = "임대인 성별은 필수값입니다.")
    private LessorGender lessorGender;

    @NotBlank(message = "임대인 나이는 필수값입니다.")
    private LessorAge lessorAge;

    @NotNull(message = "임대인에 대한 정보는 null값이면 안됩니다.")
    private String lessorReview;

    @NotBlank(message = "방갯수 필수값입니다.")
    private RoomCount roomCount;

    @NotBlank(message = "방음은 필수값입니다.")
    private MultiChoiceReview soundInsulation;

    @NotBlank(message = "해충은 필수값입니다.")
    private MultiChoiceReview pest;

    @NotBlank(message = "채광은 필수값입니다.")
    private MultiChoiceReview lightning;

    @NotBlank(message = "수압은 필수값입니다.")
    private MultiChoiceReview waterPressure;

    private List<Furniture> furnitures;

    @NotBlank(message = "상세리뷰는 필수값입니다.")
    private String review;

    @NotBlank(message = "종합평가는 필수값입니다.")
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
