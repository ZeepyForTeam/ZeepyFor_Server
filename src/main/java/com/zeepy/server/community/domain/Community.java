package com.zeepy.server.community.domain;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.OverflowAchievementRateException;
import com.zeepy.server.user.domain.User;
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
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;

    @Nullable
    private String productName;

    @Nullable
    private Integer productPrice;

    @Nullable
    private String purchasePlace;

    @Nullable
    private String sharingMethod;

    @Nullable
    private Integer targetNumberOfPeople;

    @Column(columnDefinition = "integer default 0")
    private Integer currentNumberOfPeople;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Nullable
    private String place;

    @Nullable
    private String instructions;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "community")
    private List<CommunityLike> likeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    private List<Participation> participationsList = new ArrayList<>();

    @ElementCollection
    @JoinTable(name = "communityImageUrls", joinColumns = @JoinColumn(name = "community_id"))
    private List<String> imageUrls;

    @Builder
    public Community(
            Long id,
            CommunityCategory communityCategory,
            String productName,
            Integer productPrice,
            String purchasePlace,
            String sharingMethod,
            Integer targetNumberOfPeople,
            Integer currentNumberOfPeople,
            User user,
            String title,
            String content,
            String place,
            String instructions,
            List<String> imageUrls
    ) {
        this.id = id;
        this.communityCategory = communityCategory;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sharingMethod = sharingMethod;
        this.targetNumberOfPeople = targetNumberOfPeople;
        this.currentNumberOfPeople = currentNumberOfPeople;
        this.purchasePlace = purchasePlace;
        this.user = user;
        this.title = title;
        this.place = place;
        this.content = content;
        this.instructions = instructions;
        this.imageUrls = imageUrls;
    }

    public void setCurrentNumberOfPeople() {
        if (communityCategory == CommunityCategory.JOINTPURCHASE && targetNumberOfPeople != null) {
            this.currentNumberOfPeople++;
        }
        if (targetNumberOfPeople != null && targetNumberOfPeople < currentNumberOfPeople) {
            throw new OverflowAchievementRateException();
        }
    }
}
