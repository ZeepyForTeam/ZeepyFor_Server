package com.zeepy.server.community.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_sequence_gen")
    @SequenceGenerator(name = "community_sequence_gen", sequenceName = "community_sequence")
    private Long id;    //id

    @NotNull
    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;    //커뮤니티 카테고리

    @Nullable
    private String productName; //상품명

    @Nullable
    private Integer productPrice;    //상품가격

    @Nullable
    private String sharingMethod;   //나눔방식

    @Nullable
    private Integer targetNumberOfPeople;   //목표인원

    @Nullable
    private Integer targetAmount;   //금액

    @NotNull
    private String title;   //제목

    @NotNull
    private String content; //내용

    @OneToMany(mappedBy = "community")
    private List<CommunityLike> likeUsers = new ArrayList<>();

    @Deprecated
    private String Comments;

    @Deprecated
    private String AchievementRate;

    @OneToMany(mappedBy = "community")
    private List<Participation> participationsList;

    @ElementCollection
    @JoinTable(name = "communityImageUrls", joinColumns = @JoinColumn(name = "communityID"))
    private List<String> imageUrls;   //사진

    @Builder
    public Community(
            CommunityCategory communityCategory,
            String productName,
            Integer productPrice,
            String sharingMethod,
            Integer targetNumberOfPeople,
            Integer targetAmount,
            String title,
            String content,
            List<String> imageUrls
    ) {
        this.communityCategory = communityCategory;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sharingMethod = sharingMethod;
        this.targetNumberOfPeople = targetNumberOfPeople;
        this.targetAmount = targetAmount;
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
    }
}
