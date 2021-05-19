package com.zeepy.server.community.domain;

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
    private Long id;    //id

    @NotNull
    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;    //커뮤니티 카테고리

    @Nullable
    private String productName;

    @Nullable
    private Integer productPrice;

    @Nullable
    private String sharingMethod;

    @Nullable
    private Integer targetNumberOfPeople;

    @Nullable
    private Integer targetAmount;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "community")
    private List<CommunityLike> likeUsers = new ArrayList<>();

    @Deprecated
    private String comment;

    @Deprecated
    private String AchievementRate;

    @OneToMany(mappedBy = "community")
    private List<Participation> participationsList = new ArrayList<>();

    @ElementCollection
    @JoinTable(name = "communityImageUrls", joinColumns = @JoinColumn(name = "communityID"))
    private List<String> imageUrls;   //사진

    @Builder
    public Community(
            Long id,
            CommunityCategory communityCategory,
            String productName,
            Integer productPrice,
            String sharingMethod,
            Integer targetNumberOfPeople,
            Integer targetAmount,
            User user,
            String title,
            String content,
            List<String> imageUrls
    ) {
        this.id = id;
        this.communityCategory = communityCategory;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sharingMethod = sharingMethod;
        this.targetNumberOfPeople = targetNumberOfPeople;
        this.targetAmount = targetAmount;
        this.user = user;
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
