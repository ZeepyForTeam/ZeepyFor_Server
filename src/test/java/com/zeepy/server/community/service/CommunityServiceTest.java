package com.zeepy.server.community.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunitySimpleResDto;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipResponseDto;
import com.zeepy.server.community.repository.CommentRepository;
import com.zeepy.server.community.repository.CommunityLikeRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.push.util.FirebaseCloudMessageUtility;
import com.zeepy.server.user.domain.Role;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

@DisplayName("커뮤니티_서비스_테스트")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class CommunityServiceTest {
	private final String dummyEmail = "Zeepy@test.com";
	private final User likeUser = User.builder()
		.id(2L)
		.name("좋아요 누른 유저")
		.email(dummyEmail)
		.build();
	private final User joinUser = User.builder()
		.id(1L)
		.name("참여자")
		.build();
	private final Community joinPurhcaseCommunity = Community.builder()
		.id(1L)
		.communityCategory(CommunityCategory.JOINTPURCHASE)
		.productName("공동구매물건")
		.sharingMethod("만나서")
		.targetNumberOfPeople(2)
		.currentNumberOfPeople(0)
		.user(joinUser)
		.title("같이 살사람")
		.content("구해요")
		.imageUrls(Arrays.asList("1", "2", "3"))
		.build();
	private CommunityService communityService;
	@Mock
	private CommunityRepository communityRepository;
	@Mock
	private CommunityLikeRepository communityLikeRepository;
	@Mock
	private ParticipationRepository participationRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private CommentRepository commentRepository;
	@Mock
	private FirebaseCloudMessageUtility firebaseCloudMessageUtility;

	@BeforeEach
	public void setUp() {
		this.communityService = new CommunityService(communityRepository, communityLikeRepository, userRepository,
			participationRepository, commentRepository, firebaseCloudMessageUtility);
	}

	@DisplayName("참여하기_서비스로직_테스트")
	@Transactional
	@Test
	public void joinCommunity() {
		//given
		User user = joinUser;
		String userEmail = "test@naver.com";
		Community community = joinPurhcaseCommunity;
		Long communityId = community.getId();
		Participation participation = createParticipation(community, user);

		JoinCommunityRequestDto requestDto = new JoinCommunityRequestDto("댓글", true);

		when(communityRepository.findById(any(Long.class))).thenReturn(Optional.of(community));
		when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
		lenient().when(participationRepository.findById(any(Long.class))).thenReturn(Optional.of(participation));
		when(participationRepository.save(any(Participation.class))).thenReturn(participation);
		when(participationRepository.findAll()).thenReturn(Collections.singletonList(participation));

		//when
		communityService.joinCommunity(communityId, requestDto, userEmail);

		//then
		List<Participation> participationList = participationRepository.findAll();
		assertThat(participationList.size()).isEqualTo(1);
	}

	public Participation createParticipation(Community community, User user) {
		return Participation.builder()
			.id(1L)
			.community(community)
			.user(user)
			.build();
	}

	@DisplayName("커뮤니티 상세 보기 테스트")
	@Test
	public void getCommunity() {
		// given - likeUser가 joinPurchaseCommunity 글에 좋아요를 눌렀다
		CommunityLike communityLike = new CommunityLike(1L, joinPurhcaseCommunity, likeUser);
		joinPurhcaseCommunity.getLikes().add(communityLike);

		when(userRepository.findByEmail(any())).thenReturn(Optional.of(likeUser));
		when(communityRepository.findById(anyLong())).thenReturn(Optional.of(joinPurhcaseCommunity));

		// when - 커뮤니티 불러오기
		CommunityResponseDto dto = communityService.getCommunity(1L, dummyEmail);

		// then
		assertThat(dto.getUser().getId()).isEqualTo(joinUser.getId());
		assertThat(dto.getIsLiked()).isTrue();
		assertThat(dto.getIsParticipant()).isFalse();
	}

	@DisplayName("마이집 조회")
	@Test
	public void getMyZipList() {
		// 현 사용자
		User user = User.builder()
			.name("겨울")
			.accessNotify(true)
			.role(Role.ROLE_USER)
			.build();

		// 내가 참여하고 있는 커뮤니티
		Community c1 = Community.builder()
			.id(1L)
			.user(likeUser)
			.communityCategory(CommunityCategory.JOINTPURCHASE)
			.address("경기도 안양시")
			.build();

		// 내가 좋아하는 커뮤니티
		Community c2 = Community.builder()
			.id(2L)
			.user(likeUser)
			.communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
			.address("경기도 안양시")
			.build();

		// 내가 작성한 커뮤니티
		Community c3 = Community.builder()
			.id(3L)
			.user(user)
			.communityCategory(CommunityCategory.FREESHARING)
			.address("경기도 안양시")
			.build();

		// 내 참여
		Participation p1 = Participation.builder()
			.id(1L)
			.community(c1)
			.user(user)
			.build();

		// 내 좋아요
		CommunityLike cl = CommunityLike.builder()
			.id(1L)
			.community(c2)
			.user(user)
			.build();

		String userEmail = "test@test.com";
		String communityCategory = "";

		//when
		when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
		when(participationRepository.findAllByUserId(any())).thenReturn(Collections.singletonList(p1));
		when(communityLikeRepository.findAllByUserId(any())).thenReturn(Collections.singletonList(cl));
		when(communityRepository.findAllByUserId(any())).thenReturn(Collections.singletonList(c3));

		//then
		List<Community> expectCommunities = new ArrayList<>();
		expectCommunities.add(c1);
		expectCommunities.add(c2);
		expectCommunities.add(c3);

		MyZipResponseDto expect = MyZipResponseDto.builder()
			.myZip(new ArrayList<>())
			.build();
		expect.addCommunities(expectCommunities);

		MyZipResponseDto actual = communityService.getMyZipList(userEmail, communityCategory);
		assertAll(() -> assertThat(actual.getMyZip().size()).isEqualTo(3),
			() -> assertThat(CommunitySimpleResDto.of(c1).getTitle()).isEqualTo(actual.getMyZip().get(0).getTitle()),
			() -> assertThat(CommunitySimpleResDto.of(c2).getTitle()).isEqualTo(actual.getMyZip().get(1).getTitle()),
			() -> assertThat(CommunitySimpleResDto.of(c3).getTitle()).isEqualTo(actual.getMyZip().get(2).getTitle())
		);
	}

}
