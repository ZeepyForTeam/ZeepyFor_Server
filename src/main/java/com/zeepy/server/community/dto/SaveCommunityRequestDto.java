package com.zeepy.server.community.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveCommunityRequestDto {

	@NotNull(message = "커뮤니티카테고리는 필수값입니다.")
	private String communityCategory;

	@NotNull(message = "커뮤니티 주소는 필수입니다.")
	private String address;

	private String productName;

	private String purchasePlace;

	private String sharingMethod;

	private Integer targetNumberOfPeople;

	private Integer currentNumberOfPeople = 0;

	@NotEmpty(message = "제목은 필수입니다.")
	private String title;

	@NotEmpty(message = "내용은 필수입니다.")
	private String content;

	private String instructions;

	private List<String> imageUrls;

	@Builder
	public SaveCommunityRequestDto(
		String communityCategory,
		String address,
		String productName,
		String purchasePlace,
		String sharingMethod,
		Integer targetNumberOfPeople,
		String title,
		String content,
		String instructions,
		List<String> imageUrls
		) {
		this.communityCategory = communityCategory;
		this.address = address;
		this.productName = productName;
		this.purchasePlace = purchasePlace;
		this.sharingMethod = sharingMethod;
		this.targetNumberOfPeople = targetNumberOfPeople;
		this.currentNumberOfPeople = 0;
		this.title = title;
		this.content = content;
		this.instructions = instructions;
		this.imageUrls = imageUrls;
	}

	public Community toEntity() {
		return Community.builder()
			.communityCategory(CommunityCategory.valueOf(communityCategory))
			.address(address)
			.productName(productName)
			.purchasePlace(purchasePlace)
			.sharingMethod(sharingMethod)
			.targetNumberOfPeople(targetNumberOfPeople)
			.currentNumberOfPeople(currentNumberOfPeople)
			.title(title)
			.content(content)
			.instructions(instructions)
			.imageUrls(imageUrls)
			.build();
	}
}
