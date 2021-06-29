// package com.zeepy.server.community.domain;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.Arrays;
// import java.util.List;
//
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
// import com.zeepy.server.common.CustomExceptionHandler.CustomException.OverflowAchievementRateException;
// import com.zeepy.server.community.dto.CommentDto;
// import com.zeepy.server.community.dto.ParticipationDto;
// import com.zeepy.server.community.dto.UpdateCommunityReqDto;
// import com.zeepy.server.community.repository.CommentRepository;
// import com.zeepy.server.community.repository.CommunityLikeRepository;
// import com.zeepy.server.community.repository.CommunityRepository;
// import com.zeepy.server.community.repository.ParticipationRepository;
// import com.zeepy.server.user.domain.User;
// import com.zeepy.server.user.repository.UserRepository;
//
// @DisplayName("CommunityDomain_테스트_클래스")
// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// public class CommunityRepositoryTest {
// 	private final User writer = User.builder()
// 		.id(1L)
// 		.name("작성자")
// 		.place("구월동")
// 		.build();
//
// 	private final Community joinPurchaseCommunity = Community.builder()
// 		.id(1L)
// 		.communityCategory(CommunityCategory.JOINTPURCHASE)
// 		.productName("공동구매물건")
// 		.productPrice(10000)
// 		.purchasePlace("매장")
// 		.sharingMethod("만나서")
// 		.targetNumberOfPeople(1)
// 		.currentNumberOfPeople(0)
// 		.user(writer)
// 		.title("같이 살사람")
// 		.content("구해요")
// 		.place("구월동")
// 		.imageUrls(Arrays.asList("1", "2", "3"))
// 		.build();
//
// 	private final Community freeSharingCommunity = Community.builder()
// 		.id(2L)
// 		.communityCategory(CommunityCategory.FREESHARING)
// 		.user(writer)
// 		.title("같이 살사람")
// 		.content("구해요")
// 		.imageUrls(Arrays.asList("1", "2", "3"))
// 		.build();
//
// 	private final Community neighborhoodfriendCommunity = Community.builder()
// 		.id(3L)
// 		.communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
// 		.user(writer)
// 		.title("같이 살사람")
// 		.content("구해요")
// 		.imageUrls(Arrays.asList("1", "2", "3"))
// 		.build();
//
// 	private final User user1 = User.builder()
// 		.id(2L)
// 		.name("참여자1")
// 		.build();
// 	private final User user2 = User.builder()
// 		.id(3L)
// 		.name("참여자2")
// 		.build();
//
// 	@Autowired
// 	CommunityRepository communityRepository;
// 	@Autowired
// 	CommunityLikeRepository communityLikeRepository;
// 	@Autowired
// 	UserRepository userRepository;
// 	@Autowired
// 	ParticipationRepository participationRepository;
// 	@Autowired
// 	CommentRepository commentRepository;
//
// 	@BeforeEach
// 	void setUp() {
// 		userRepository.save(writer);
// 	}
//
// 	@AfterEach
// 	public void reset() {
// 		communityRepository.deleteAll();
// 		userRepository.deleteAll();
// 		participationRepository.deleteAll();
// 		commentRepository.deleteAll();
// 	}
//
// 	@DisplayName("커뮤니티 저장하기 테스트")
// 	@Test
// 	public void saveCommunity() {
// 		//given
// 		communityRepository.save(freeSharingCommunity);
//
// 		//when
// 		List<Community> result = communityRepository.findAll();
//
// 		//then
// 		Community communities = result.get(0);
// 		assertThat(communities.getTitle())
// 			.isEqualTo("같이 살사람");
// 		assertThat(communities.getProductName())
// 			.isEqualTo("공동구매물건");
// 	}
//
// 	@DisplayName("참여하기 저장 테스트")
// 	@Test
// 	public void joinCommunity() {
// 		//given
// 		Community saveCommunity = communityRepository.save(neighborhoodfriendCommunity);
// 		User joinUser = userRepository.save(user1);
//
// 		Long saveCommunityId = saveCommunity.getId();
// 		Long joinUserId = joinUser.getId();
//
// 		//when
// 		ParticipationDto participationDto = new ParticipationDto(saveCommunity, joinUser);
// 		Participation participation = participationRepository.save(participationDto.toUpdateEntity());
//
// 		//then
// 		Community participationsCommunity = participation.getCommunity();
// 		User participationsUser = participation.getUser();
//
// 		assertThat(participationsCommunity.getId())
// 			.isEqualTo(saveCommunityId);
// 		assertThat(participationsUser.getId())
// 			.isEqualTo(joinUserId);
// 		assertThat(participationsUser.getName())
// 			.isEqualTo("참여자");
// 	}
//
// 	@DisplayName("MyZip참여리스트 불러오기 테스트")
// 	@Test
// 	public void getZipList() {
// 		//given
// 		User joinUser = userRepository.save(user1);
// 		Community community = communityRepository.save(joinPurchaseCommunity);
//
// 		String joinUserName = joinUser.getName();
// 		Long joinUserId = joinUser.getId();
// 		String communityProductName = community.getProductName();
//
// 		ParticipationDto requestDto = new ParticipationDto(community, joinUser);
// 		Participation participation = participationRepository.save(requestDto.toEntity());
//
// 		//when
// 		List<Participation> findParticipationList = participationRepository.findAllByUserId(joinUser.getId());
//
// 		//then
// 		Participation firstParticipation = findParticipationList.get(0);
// 		User participationsUser = firstParticipation.getUser();
// 		Community participationsCommunity = firstParticipation.getCommunity();
//
// 		assertThat(participationsUser.getId())
// 			.isEqualTo(joinUserId);
// 		assertThat(participationsUser.getName())
// 			.isEqualTo(joinUserName);
// 		assertThat(participationsCommunity.getProductName())
// 			.isEqualTo(communityProductName);
// 		assertThat(firstParticipation)
// 			.isEqualTo(participation);
// 	}
//
// 	@DisplayName("댓글달기 테스트")
// 	@Test
// 	public void setComment() {
// 		//given
// 		Community community = joinPurchaseCommunity;
//
// 		Comment comment1 = Comment.builder()
// 			.comment("댓글1")
// 			.user(user1)
// 			.build();
// 		comment1.setSuperComment(null);
// 		comment1.setCommunity(community);
//
// 		Comment comment2 = Comment.builder()
// 			.comment("댓글2")
// 			.user(user2)
// 			.build();
// 		comment2.setSuperComment(null);
// 		comment2.setCommunity(community);
//
// 		Comment subComment1 = Comment.builder()
// 			.comment("대댓글")
// 			.user(writer)
// 			.build();
// 		subComment1.setSuperComment(subComment1);
// 		subComment1.setCommunity(community);
//
// 		//when
//
// 		Comment saveComment1 = commentRepository.save(comment1);
//
// 		Comment saveComment2 = commentRepository.save(comment2);
//
// 		Comment saveSubComment1 = commentRepository.save(subComment1);
//
// 		//then
// 		List<Comment> saveComment1sSubComments = saveComment1.getSubComments();
// 		Integer comment1sSubCommentsSize = saveComment1sSubComments.size();
// 		Comment comment1sSubComment = saveComment1sSubComments.get(0);
// 		assertThat(comment1sSubCommentsSize)
// 			.isEqualTo(1);
// 		assertThat(comment1sSubComment)
// 			.isEqualTo(saveSubComment1);
//
// 		Comment comment1sSuperComment = saveComment1.getSuperComment();
// 		Community comment1sCommunity = saveComment1.getCommunity();
// 		assertThat(comment1sSuperComment)
// 			.isNull();
// 		assertThat(comment1sCommunity)
// 			.isEqualTo(community);
//
// 		List<Comment> communitysComments = community.getComments();
// 		Integer communitysCommentsSzie = communitysComments.size();
// 		Comment communitysComment = communitysComments.get(1);
// 		assertThat(communitysCommentsSzie)
// 			.isEqualTo(2);
// 		assertThat(communitysComment)
// 			.isEqualTo(saveComment2);
//
// 		Comment subCommentsSuperComment = saveSubComment1.getSuperComment();
// 		Integer subCommentsSubCommentSize = saveSubComment1.getSubComments().size();
// 		assertThat(subCommentsSuperComment)
// 			.isEqualTo(saveComment1);
// 		assertThat(subCommentsSubCommentSize)
// 			.isEqualTo(0);
// 	}
//
// 	@DisplayName("community의 commnets 테스트")
// 	@Test
// 	public void setSubComment() {
// 		//given
// 		//사용자3명(작성자, 사용자1, 사용자2)
// 		userRepository.save(writer);
// 		userRepository.save(user1);
// 		userRepository.save(user2);
// 		//커뮤니티1개
// 		//when
// 		Community saveCommunity = communityRepository.save(joinPurchaseCommunity);
// 		//사용자1,사용자2 참여
// 		ParticipationDto user1ParticipationDto = new ParticipationDto(saveCommunity, user1);
// 		Participation user1Participation = user1ParticipationDto.toEntity();
// 		participationRepository.save(user1Participation);
// 		CommentDto user1CommentDto = CommentDto.builder()
// 			.comment("댓글1")
// 			.writer(user1)
// 			.superComment(null)
// 			.community(saveCommunity)
// 			.build();
// 		Comment user1Comment = user1CommentDto.toEntity();
// 		commentRepository.save(user1Comment);
// 		Community findCommunity1 = communityRepository.findById(1L)
// 			.orElseThrow(NotFoundCommunityException::new);
//
// 		ParticipationDto user2ParticipationDto = new ParticipationDto(saveCommunity, user2);
// 		Participation user2Participation = user2ParticipationDto.toEntity();
// 		participationRepository.save(user2Participation);
// 		CommentDto user2CommentDto = CommentDto.builder()
// 			.comment("댓글2")
// 			.writer(user2)
// 			.superComment(null)
// 			.community(saveCommunity)
// 			.build();
// 		Comment user2Comment = user2CommentDto.toEntity();
// 		commentRepository.save(user2Comment);
// 		Community findCommunity2 = communityRepository.findById(1L)
// 			.orElseThrow(NotFoundCommunityException::new);
//
// 		//작성자가 사용자1 댓글에 대댓글
// 		CommentDto subCommentDto = CommentDto.builder()
// 			.comment("대댓글")
// 			.superComment(user1Comment)
// 			.community(saveCommunity)
// 			.writer(writer)
// 			.build();
// 		commentRepository.save(subCommentDto.toEntity());
// 		Community findCommunity3 = communityRepository.findById(1L)
// 			.orElseThrow(NotFoundCommunityException::new);
//
// 		//then
// 		//모든 영속성이 끝나고 community의 comments에는 대댓글이 포함되어있을까?
// 		assertThat(saveCommunity.getComments().size())
// 			.isEqualTo(2);
// 		//findById로 찾을때
// 		assertThat(findCommunity1.getComments().size())
// 			.isEqualTo(1);
// 		assertThat(findCommunity2.getComments().size())
// 			.isEqualTo(2);
// 		assertThat(findCommunity3.getComments().size())
// 			.isEqualTo(3);
// 	}
//
// 	@DisplayName("community_currentNumberOfPeople_create Deafault 0")
// 	@Test
// 	public void defaultCurrentNumberOfPeople_zero() {
// 		userRepository.save(writer);
// 		Community saveCommunity = communityRepository.save(joinPurchaseCommunity);
//
// 		assertThat(saveCommunity.getCurrentNumberOfPeople())
// 			.isEqualTo(0);
// 	}
//
// 	@DisplayName("달성률_에러_테스트")
// 	@Test
// 	@Transactional
// 	public void achievementRate_Error() {
// 		User user1 = User.builder()
// 			.id(2L)
// 			.name("참여자1")
// 			.build();
// 		User user2 = User.builder()
// 			.id(3L)
// 			.name("참여자2")
// 			.build();
// 		userRepository.save(writer);
// 		userRepository.save(user1);
// 		userRepository.save(user2);
//
// 		Community saveCommunity = communityRepository.save(joinPurchaseCommunity);
// 		communityRepository.save(freeSharingCommunity);
//
// 		saveCommunity.addCurrentNumberOfPeople();
// 		communityRepository.saveAndFlush(saveCommunity);
// 		assertThatThrownBy(saveCommunity::addCurrentNumberOfPeople).isInstanceOf(
// 			OverflowAchievementRateException.class);
// 	}
//
// 	@DisplayName("달성률_테스트")
// 	// @Test(expected = OverflowAchievementRateException.class)
// 	@Test
// 	@Transactional
// 	public void achievementRate() {
// 		User user1 = User.builder().id(2L).name("참여자1").build();
// 		userRepository.save(writer);
// 		userRepository.save(user1);
//
// 		Community saveCommunity = communityRepository.save(joinPurchaseCommunity);
//
// 		saveCommunity.addCurrentNumberOfPeople();
//
// 		// assertThat(saveCommunity.getCurrentNumberOfPeople())
// 		// 	.isEqualTo(1);
// 		assertThatThrownBy(saveCommunity::getCurrentNumberOfPeople)
// 			.isInstanceOf(OverflowAchievementRateException.class);
// 	}
//
// 	@DisplayName("커뮤니티_수정하기_테스트")
// 	@Test
// 	@Transactional
// 	public void updateCommunityTest() {
// 		userRepository.save(writer);
// 		communityRepository.saveAndFlush(joinPurchaseCommunity);
// 		long joinPurchaseCommunityId = joinPurchaseCommunity.getId();
//
// 		UpdateCommunityReqDto updateCommunityReqDto = new UpdateCommunityReqDto("수정된 제목", "수정된 제품명", 9999, "수정된 구매장소",
// 			"수정된 공유방법", 3, "수정된 설명");
//
// 		Community findCommunity = communityRepository.findById(joinPurchaseCommunityId)
// 			.orElseThrow(NotFoundCommunityException::new);
//
// 		updateCommunityReqDto.updateCommunity(findCommunity);
// 		communityRepository.flush();
//
// 		Community compareCommunity = communityRepository.findById(joinPurchaseCommunityId)
// 			.orElseThrow(NotFoundCommunityException::new);
//
// 		assertThat(compareCommunity.getTitle())
// 			.isEqualTo("수정된 제목");
// 		assertThat(compareCommunity.getProductPrice())
// 			.isNotEqualTo(joinPurchaseCommunity
// 				.getProductPrice());
// 	}
// }
