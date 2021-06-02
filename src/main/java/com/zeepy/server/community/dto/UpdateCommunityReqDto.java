package com.zeepy.server.community.dto;

import com.zeepy.server.community.domain.Community;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateCommunityReqDto {
    @NotBlank(message = "제목은 필수 사항입니다.")
    private String title;

    private String productName;

    private Integer productPrice;

    private String purchasePlace;

    private String sharingMethod;

    private Integer targetNumberOfPeople;

    private String instructions;

    public void setUpdateCommunity(Community community) {
        community.setUpdate(title,
                productName,
                productPrice,
                purchasePlace,
                sharingMethod,
                targetNumberOfPeople,
                instructions);
    }
}
