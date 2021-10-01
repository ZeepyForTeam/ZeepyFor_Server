package com.zeepy.server.review.domain;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by KimGyeong 4/19/20.
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "review_sequence_gen")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CommuncationTendency communicationTendency;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LessorGender lessorGender;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private LessorAge lessorAge;

    @NotEmpty
    @Lob
    private String lessorReview;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomCount roomCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MultiChoiceReview soundInsulation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MultiChoiceReview pest;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MultiChoiceReview lightning;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MultiChoiceReview waterPressure;

    @ElementCollection(targetClass = Furniture.class)
    @JoinTable(name = "tblFurnitures", joinColumns = @JoinColumn(name = "reviewID"))
    @Column(name = "furniture")
    @Enumerated(EnumType.STRING)
    private List<Furniture> furnitures;

    @NotEmpty
    @Lob
    private String review;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TotalEvaluation totalEvaluation;

    @ElementCollection
    @JoinTable(name = "tblImageUrls", joinColumns = @JoinColumn(name = "reviewID"))
    @Column(name = "imageUrl")
    private List<String> imageUrls;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "building_id")
    private Building building;

    @Builder
    public Review(
        Long id,
        String address,
        CommuncationTendency communicationTendency,
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
        List<String> imageUrls,
        Building building
    ) {
        this.id = id;
        this.address = address;
        this.communicationTendency = communicationTendency;
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
        this.building = building;
    }

    public void setUser(User user) {
        this.user = user;
        user.getReviews().add(this);
    }
}
