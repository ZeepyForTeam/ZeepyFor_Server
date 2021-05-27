package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SaveCommunityRequestDto {

    @NotNull(message = "커뮤니티카테고리는 필수값입니다.")
    private CommunityCategory communityCategory;

    private String productName;

    private Integer productPrice;

    private String purchasePlace;

    private String sharingMethod;

    private Integer targetNumberOfPeople;

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;

    private List<String> imageUrls;

    private User user;

    private Long writerId;//작성자ID인데 토큰작업되면 지울꺼

    @Builder
    public SaveCommunityRequestDto(CommunityCategory communityCategory,
                                   String productName,
                                   Integer productPrice,
                                   String purchasePlace,
                                   String sharingMethod,
                                   Integer targetNumberOfPeople,
                                   String title,
                                   String content,
                                   User user,
                                   List<String> imageUrls) {
        this.communityCategory = communityCategory;
        this.productName = productName;
        this.productPrice = productPrice;
        this.purchasePlace = purchasePlace;
        this.sharingMethod = sharingMethod;
        this.targetNumberOfPeople = targetNumberOfPeople;
        this.title = title;
        this.content = content;
        this.user = user;
        this.imageUrls = imageUrls;
    }

    public Community toEntity() {
        return Community.builder()
                .communityCategory(communityCategory)
                .productName(productName)
                .purchasePlace(purchasePlace)
                .sharingMethod(sharingMethod)
                .targetNumberOfPeople(targetNumberOfPeople)
                .title(title)
                .content(content)
                .user(user)
                .imageUrls(imageUrls)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
