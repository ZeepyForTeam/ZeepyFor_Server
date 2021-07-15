package com.zeepy.server.review.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.annotation.Enum;
import com.zeepy.server.review.domain.CommuncationTendency;
import com.zeepy.server.review.domain.Furniture;
import com.zeepy.server.review.domain.LessorAge;
import com.zeepy.server.review.domain.LessorGender;
import com.zeepy.server.review.domain.MultiChoiceReview;
import com.zeepy.server.review.domain.Review;
import com.zeepy.server.review.domain.RoomCount;
import com.zeepy.server.review.domain.TotalEvaluation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {

    private Long user; // 변경 될 겁니다.

    @NotNull
    private String address;

    @Enum(enumClass = CommuncationTendency.class, ignoreCase = true, message = "소통경향은 필수값입니다.")
    private String communcationTendency;

    @Enum(enumClass = LessorGender.class, ignoreCase = true, message = "임대인 성별은 필수값입니다.")
    private String lessorGender;

    @Enum(enumClass = LessorAge.class, ignoreCase = true, message = "임대인 나이는 필수값입니다.")
    private String lessorAge;

    @NotNull
    private String lessorReview;

    @Enum(enumClass = RoomCount.class, ignoreCase = true, message = "방갯수 필수값입니다.")
    private String roomCount;

    @Enum(enumClass = MultiChoiceReview.class, ignoreCase = true, message = "방음은 필수값입니다.")
    private String soundInsulation;

    @Enum(enumClass = MultiChoiceReview.class, ignoreCase = true, message = "해충은 필수값입니다.")
    private String pest;

    @Enum(enumClass = MultiChoiceReview.class, ignoreCase = true, message = "채광은 필수값입니다.")
    private String lightning;

    @Enum(enumClass = MultiChoiceReview.class, ignoreCase = true, message = "수압은 필수값입니다.")
    private String waterPressure;

    private List<Furniture> furnitures;

    @NotBlank(message = "상세리뷰는 필수값입니다.")
    private String review;

    @Enum(enumClass = TotalEvaluation.class, ignoreCase = true, message = "종합평가는 필수값입니다.")
    private String totalEvaluation;

    private List<String> imageUrls;

    @NotNull
    private Long buildingId;

    @Builder
    public ReviewDto(
            Long user, String address,
            String communcationTendency,
            String lessorGender,
            String lessorAge,
            String lessorReview,
            String roomCount,
            String soundInsulation,
            String pest,
            String lightning,
            String waterPressure,
            List<Furniture> furnitures,
            String review,
            String totalEvaluation,
            List<String> imageUrls,
            Long buildingId
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
        this.buildingId = buildingId;
    }

    public Review returnReviewEntity() {
        return new Review(
                null,
                this.user,
                this.address,
                CommuncationTendency.valueOf(this.communcationTendency),
                LessorGender.valueOf(this.lessorGender),
                LessorAge.valueOf(this.lessorAge),
                this.lessorReview,
                RoomCount.valueOf(this.roomCount),
                MultiChoiceReview.valueOf(this.soundInsulation),
                MultiChoiceReview.valueOf(this.pest),
                MultiChoiceReview.valueOf(this.lightning),
                MultiChoiceReview.valueOf(this.waterPressure),
                this.furnitures,
                this.review,
                TotalEvaluation.valueOf(this.totalEvaluation),
                this.imageUrls,
                null
        );
    }
}
