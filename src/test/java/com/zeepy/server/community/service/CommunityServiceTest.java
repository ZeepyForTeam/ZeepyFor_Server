// package com.zeepy.server.community.service;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.Arrays;
// import java.util.List;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.zeepy.server.community.domain.Community;
// import com.zeepy.server.community.domain.CommunityCategory;
// import com.zeepy.server.community.domain.Participation;
// import com.zeepy.server.community.dto.JoinCommunityRequestDto;
// import com.zeepy.server.community.repository.CommentRepository;
// import com.zeepy.server.community.repository.CommunityLikeRepository;
// import com.zeepy.server.community.repository.CommunityRepository;
// import com.zeepy.server.community.repository.ParticipationRepository;
// import com.zeepy.server.user.domain.User;
// import com.zeepy.server.user.repository.UserRepository;
//
// @DisplayName("커뮤니티_서비스_테스트")
// @SpringBootTest
// @Transactional
// public class CommunityServiceTest {
// 	private final User joinUser = User.builder()
// 		.id(1L)
// 		.name("참여자")
// 		.place("구월동")
// 		.build();
// 	private final Community joinPurhcaseCommunity = Community.builder()
// 		.id(1L)
// 		.communityCategory(CommunityCategory.JOINTPURCHASE)
// 		.productName("공동구매물건")
// 		.productPrice(10000)
// 		.place("구월동")
// 		.sharingMethod("만나서")
// 		.targetNumberOfPeople(2)
// 		.currentNumberOfPeople(0)
// 		.user(joinUser)
// 		.title("같이 살사람")
// 		.content("구해요")
// 		.imageUrls(Arrays.asList("1", "2", "3"))
// 		.build();
// 	private CommunityService communityService;
// 	@Autowired
// 	private CommunityRepository communityRepository;
// 	@Autowired
// 	private ParticipationRepository participationRepository;
// 	@Autowired
// 	private UserRepository userRepository;
// 	@Autowired
// 	private CommentRepository commentRepository;
// 	@Autowired
// 	private CommunityLikeRepository communityLikeRepository;
//
// 	@BeforeEach
// 	public void setDI() {
// 		this.communityService = new CommunityService(communityRepository, communityLikeRepository, userRepository,
// 			participationRepository,
// 			commentRepository);
// 	}
//
// 	@DisplayName("참여하기_서비스로직_테스트")
// 	@Test
// 	public void joinCommunity() {
// 		//given
// 		User user = joinUser;
// 		Long userId = user.getId();
// 		Community community = joinPurhcaseCommunity;
// 		Long communityId = community.getId();
// 		Participation participation = createParticipation(community, user);
//
// 		JoinCommunityRequestDto requestDto = new JoinCommunityRequestDto(null, true, userId);
//
// 		//when
// 		communityService.joinCommunity(communityId, requestDto);
//
// 		//then
// 		List<Participation> participationList = participationRepository.findAll();
// 		assertThat(participationList.size()).isEqualTo(1);
// 	}
//
// 	public Participation createParticipation(Community community, User user) {
// 		return Participation.builder()
// 			.id(1L)
// 			.community(community)
// 			.user(user)
// 			.build();
// 	}
// }
