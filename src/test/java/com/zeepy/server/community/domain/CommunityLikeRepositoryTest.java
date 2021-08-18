// package com.zeepy.server.community.domain;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.List;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
//
// import com.zeepy.server.community.dto.CommunityLikeDto;
// import com.zeepy.server.community.repository.CommunityLikeRepository;
// import com.zeepy.server.community.repository.CommunityRepository;
// import com.zeepy.server.user.domain.Role;
// import com.zeepy.server.user.domain.User;
// import com.zeepy.server.user.repository.UserRepository;
//
// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// public class CommunityLikeRepositoryTest {
// 	@Autowired
// 	CommunityRepository communityRepository;
//
// 	@Autowired
// 	CommunityLikeRepository communityLikeRepository;
//
// 	@Autowired
// 	UserRepository userRepository;
//
// 	private final User userA = User.builder()
// 		.name("A")
// 		.role(Role.ROLE_USER)
// 		.accessNotify(true)
// 		.build();
//
// 	private final User userB = User.builder()
// 		.name("B")
// 		.role(Role.ROLE_USER)
// 		.accessNotify(false)
// 		.build();
//
// 	@DisplayName("좋아요 누른 커뮤니티 GET 테스트")
// 	@Test
// 	public void getLikeList() {
// 		User A = userRepository.save(userA);
// 		User B = userRepository.save(userB);
// 		Community writtenByA = communityRepository.save(makeDummyJointPurchase(A));
// 		Community writtenByB = communityRepository.save(makeDummyFreeSharing(B));
// 		CommunityLikeDto ALikesADto = new CommunityLikeDto(A, writtenByA);
// 		CommunityLikeDto ALikesBDto = new CommunityLikeDto(A, writtenByB);
// 		CommunityLike ALikesA = communityLikeRepository.save(ALikesADto.toEntity());
// 		CommunityLike ALikesB = communityLikeRepository.save(ALikesBDto.toEntity());
//
// 		List<CommunityLike> communityLikeList = communityLikeRepository.findAll();
//
// 		CommunityLike firstLike = communityLikeList.get(0);
// 		CommunityLike secondLike = communityLikeList.get(1);
//
// 		assertThat(firstLike.getUser().getId()).isEqualTo(A.getId());
// 		assertThat(secondLike.getUser()).isEqualTo(A);
// 		assertThat(firstLike.getCommunity().getCommunityCategory()).isEqualTo(writtenByA.getCommunityCategory());
// 		assertThat(secondLike.getCommunity()).isEqualTo(writtenByB);
// 	}
//
// 	public Community makeDummyJointPurchase(User user) {
// 		return Community.builder()
// 			.communityCategory(CommunityCategory.JOINTPURCHASE)
// 			.productName("사료")
// 			.user(user)
// 			.address("사당")
// 			.title("고앵이 사료 공구")
// 			.content("고양이 확대 같이 해봐요")
// 			.targetNumberOfPeople(4)
// 			.build();
// 	}
//
// 	public Community makeDummyFreeSharing(User user) {
// 		return Community.builder()
// 			.communityCategory(CommunityCategory.FREESHARING)
// 			.productName("기프티콘")
// 			.user(user)
// 			.address("향남")
// 			.title("오늘까지인 기프티콘 나눔")
// 			.content("죄송해요 그냥 제가 쓸래요")
// 			.build();
// 	}
//
// }
