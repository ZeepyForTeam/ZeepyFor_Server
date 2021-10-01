package com.zeepy.server.community.domain;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.OverflowAchievementRateException;
import com.zeepy.server.common.domain.BaseTimeEntity;
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
public class Community extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "community_sequence_gen")
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CommunityCategory communityCategory;

	@NotNull
	private String address;

	@Nullable
	private String productName;

	@Nullable
	private String productPrice;

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
	private String instructions;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "community", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<CommunityLike> likes = new ArrayList<>();

	@OneToMany(mappedBy = "community", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "community", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Participation> participationsList = new ArrayList<>();

	@ElementCollection
	@JoinTable(name = "communityImageUrls", joinColumns = @JoinColumn(name = "community_id"))
	private List<String> imageUrls;

	@Builder
	public Community(
		Long id,
		CommunityCategory communityCategory,
		String address,
		String productName,
		String productPrice,
		String purchasePlace,
		String sharingMethod,
		Integer targetNumberOfPeople,
		Integer currentNumberOfPeople,
		User user,
		String title,
		String content,
		String instructions,
		List<String> imageUrls
	) {
		this.id = id;
		this.communityCategory = communityCategory;
		this.address = address;
		this.productName = productName;
		this.productPrice = productPrice;
		this.purchasePlace = purchasePlace;
		this.sharingMethod = sharingMethod;
		this.targetNumberOfPeople = targetNumberOfPeople;
		this.currentNumberOfPeople = currentNumberOfPeople;
		this.user = user;
		this.title = title;
		this.content = content;
		this.instructions = instructions;
		this.imageUrls = imageUrls;
	}

	public void addCurrentNumberOfPeople() {
		if (communityCategory == CommunityCategory.JOINTPURCHASE && targetNumberOfPeople != null) {
			this.currentNumberOfPeople++;
		}
		if (targetNumberOfPeople != null && targetNumberOfPeople < currentNumberOfPeople) {
			throw new OverflowAchievementRateException();
		}
	}

	public Boolean checkCurrentNumberOfPeople() {
		if (targetNumberOfPeople != null && targetNumberOfPeople <= currentNumberOfPeople) {
			return true;
		} else {
			return false;
		}
	}

	public void substractCurrentNumberOfPeople() {
		currentNumberOfPeople--;
	}

	public void update(
		String title,
		String content,
		String productName,
		String productPrice,
		String purchasePlace,
		String sharingMethod,
		Integer targetNumberOfPeople,
		String instructions) {
		this.title = title;
		this.content = content;
		this.productName = productName;
		this.purchasePlace = purchasePlace;
		this.sharingMethod = sharingMethod;
		this.targetNumberOfPeople = targetNumberOfPeople;
		this.instructions = instructions;
	}

	public void setUser(User user) {
		this.user = user;
		user.getCommunities().add(this);
	}
}
