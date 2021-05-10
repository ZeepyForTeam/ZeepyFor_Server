package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class SaveCommunityRequestDto {
    private CommunityCategory communityCategory;
    private String productName;
    private Integer productPrice;
    private String sharingMethod;
    private Integer targetNumberOfPeople;
    private Integer targetAmount;
    private String title;
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
                                   List<String> imageUrls){
        this.communityCategory=communityCategory;
        this.productName=productName;
        this.productPrice=productPrice;
        this.sharingMethod=sharingMethod;
        this.targetNumberOfPeople=targetNumberOfPeople;
        this.targetAmount=targetAmount;
        this.title=title;
        this.content=content;
        this.imageUrls=imageUrls;
    }

    public Community toEntity(){
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
