package com.zeepy.server.review.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by KimGyeong 4/19/20.
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_sequence_gen")
	@SequenceGenerator(name = "review_sequence_gen", sequenceName = "review_sequence")
	private Long id;

	/**
	 * User class로 변경 될 예정. by KimGyeong
	 */
	@Deprecated
	private Long user;

	/**
	 * 주소 혹은 OPEN API에서 사용하는 ID로 변경 될 예정. by KimGyeong
	 */
	@Deprecated
	private String address;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CommuncationTendency communcationTendency;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LessorGender lessorGender;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private LessorAge lessorAge;

	@NotEmpty
	@Lob
	private String lessorReview;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RoomCount roomCount;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MultiChoiceReview soundInsulation;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MultiChoiceReview pest;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MultiChoiceReview lightning;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MultiChoiceReview waterPressure;

	@ElementCollection(targetClass = Furniture.class)
	@JoinTable(name = "tblFurnitures", joinColumns = @JoinColumn(name = "reviewID"))
	@Column(name = "furniture")
	@Enumerated(EnumType.STRING)
	private List<Furniture> furnitures;

	@NotEmpty
	@Lob
	private String review;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TotalEvaluation totalEvaluation;

	@ElementCollection
	@JoinTable(name = "tblImageUrls", joinColumns = @JoinColumn(name = "reviewID"))
	@Column(name = "imageUrl")
	private List<String> imageUrls;

	@Builder
	public Review(Long id, Long user, String address,
		CommuncationTendency communcationTendency,
		LessorGender lessorGender,
		LessorAge lessorAge,
		String lessorReview,
		RoomCount roomCount,
		MultiChoiceReview soundInsulation,
		MultiChoiceReview pest,
		MultiChoiceReview lightning,
		MultiChoiceReview waterPressure,
		List<Furniture> furnitures,
		String review,
		TotalEvaluation totalEvaluation,
		List<String> imageUrls) {
		this.id = id;
		this.user = user;
		this.address = address;
		this.communcationTendency = communcationTendency;
		this.lessorGender = lessorGender;
		this.lessorAge = lessorAge;
		this.lessorReview = lessorReview;
		this.roomCount = roomCount;
		this.soundInsulation = soundInsulation;
		this.pest = pest;
		this.lightning = lightning;
		this.waterPressure = waterPressure;
		this.furnitures = furnitures;
		this.review = review;
		this.totalEvaluation = totalEvaluation;
		this.imageUrls = imageUrls;
	}
}
