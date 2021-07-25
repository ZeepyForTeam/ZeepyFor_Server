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
	private CommunityCategory communityCategory;

	private String productName;

	private Integer productPrice;

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

	private User user;

	@Builder
	public SaveCommunityRequestDto(CommunityCategory communityCategory,
		String productName,
		Integer productPrice, String purchasePlace,
		String sharingMethod,
		Integer targetNumberOfPeople,
		String title,
		String content,
		String instructions,
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
		this.instructions = instructions;
		this.user = user;
		this.imageUrls = imageUrls;
	}

	public Community toEntity() {
		return Community.builder()
			.communityCategory(communityCategory)
			.productName(productName)
			.productPrice(productPrice)
			.purchasePlace(purchasePlace)
			.sharingMethod(sharingMethod)
			.targetNumberOfPeople(targetNumberOfPeople)
			.currentNumberOfPeople(currentNumberOfPeople)
			.title(title)
			.content(content)
			.place(user.getPlace())
			.instructions(instructions)
			.user(user)
			.imageUrls(imageUrls)
			.build();
	}

	public void setUser(User user) {
		this.user = user;
	}
}
