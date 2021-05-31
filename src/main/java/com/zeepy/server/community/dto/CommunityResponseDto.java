package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommunityResponseDto {
    private Long id;
    private CommunityCategory communityCategory;
    private String productName;
    private Integer productPrice;
    private String sharingMethod;
    private Integer targetNumberOfPeople;
    private Integer targetAmount;
    private String title;
    private String content;
    private User user;
    private List<CommunityLike> likes;
    private String comments;
    private String achievementRate;
    private List<Participation> participationList;
    private List<String> imageUrls;

    public CommunityResponseDto(Community community) {
        this.id = community.getId();
        this.user = community.getUser();
        this.communityCategory = community.getCommunityCategory();
        this.productName = community.getProductName();
        this.productPrice = community.getProductPrice();
        this.sharingMethod = community.getSharingMethod();
        this.targetNumberOfPeople = community.getTargetNumberOfPeople();
        this.targetAmount = community.getTargetAmount();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.likes = community.getLikes();
        this.comments = community.getComments();
        this.achievementRate = community.getAchievementRate();
        this.participationList = community.getParticipationsList();
        this.imageUrls = community.getImageUrls();
    }
}
