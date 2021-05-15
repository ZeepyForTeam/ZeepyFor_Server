package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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

    private String sharingMethod;

    private Integer targetNumberOfPeople;

    private Integer targetAmount;

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수입니다.")
    private String content;

    List<String> imageUrls;

    @Builder
    public SaveCommunityRequestDto(CommunityCategory communityCategory,
                                   String productName,
                                   Integer productPrice,
                                   String sharingMethod,
                                   Integer targetNumberOfPeople,
                                   Integer targetAmount,
                                   String title,
                                   String content,
                                   List<String> imageUrls) {
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

    public Community toEntity() {
        return Community.builder()
                .communityCategory(communityCategory)
                .productName(productName)
                .sharingMethod(sharingMethod)
                .targetNumberOfPeople(targetNumberOfPeople)
                .targetAmount(targetAmount)
                .title(title)
                .content(content)
                .imageUrls(imageUrls)
                .build();
    }
}
