package com.zeepy.server.community.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.auth.service.CustomDetailsService;
import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.CommunityLikeRequestDto;
import com.zeepy.server.community.dto.CommunityLikeResDto;
import com.zeepy.server.community.dto.CommunityLikeResDtos;
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunityResponseDtos;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipJoinResDto;
import com.zeepy.server.community.dto.ParticipationResDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.dto.UpdateCommunityReqDto;
import com.zeepy.server.community.dto.WriteCommentRequestDto;
import com.zeepy.server.community.dto.WriteOutResDto;
import com.zeepy.server.community.service.CommunityService;
import com.zeepy.server.user.domain.User;

@DisplayName("커뮤니티_컨트롤러_테스트")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {CommunityController.class}, includeFilters = @ComponentScan.Filter(classes = {
	EnableWebSecurity.class}))
@MockBean(JpaMetamodelMappingContext.class)
public class CommunityControllerTest extends ControllerTest {
	@MockBean
	private CommunityService communityService;
	@MockBean
	CustomDetailsService customDetailsService;

	private CommunityLikeRequestDto communityLikeRequestDto = CommunityLikeRequestDto.builder()
		.communityId(1L)
		.userId(1L)
		.build();

	private final String userEmail = "test@naver.com";

	@Override
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		super.setUp(webApplicationContext);
	}

	@DisplayName("커뮤니티_등록_테스트")
	@Test
	@WithMockUser(username = "user", password = "123123", roles = "USER")
	public void save() throws Exception {
		SaveCommunityRequestDto requestDto = SaveCommunityRequestDto.builder()
			.address("안양")
			.communityCategory(CommunityCategory.FREESHARING)
			.title("강의 공동 구매해요!")
			.content("제곧내")
			.imageUrls(Arrays.asList("asdasd", "aaaaaaa", "ccccccccc"))
			.build();

		given(communityService.save(any(), any())).willReturn(1L);

		doPost("/api/community", requestDto);
	}

	@DisplayName("좋아요_추가_테스트")
	@Test
	public void like() throws Exception {
		given(communityService.like(any(CommunityLikeRequestDto.class))).willReturn(1L);
		doPost("/api/community/like", communityLikeRequestDto);
	}

	@DisplayName("좋아요_취소_테스트")
	@Test
	public void cancelLike() throws Exception {
		doNothing().when(communityService).cancelLike(communityLikeRequestDto);
		doDelete("/api/community/like", communityLikeRequestDto);
	}

	@DisplayName("좋아요_누른_커뮤니티_불러오기_테스트")
	@Test
	public void getLikeList() throws Exception {
		List<CommunityLikeResDto> dtoList = new ArrayList<>();
		CommunityLikeResDtos dtos = new CommunityLikeResDtos(dtoList);
		given(communityService.getLikeList(any(Long.class), any(String.class))).willReturn(dtos);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("id", "1");
		doGet("/api/community/likes", params);
	}

	@DisplayName("참가하기_테스트")
	@Test
	@WithMockUser("test@naver.com")
	public void joinCommunity() throws Exception {
		//given
		long communityId = 1L;
		long joinUserId = 2L;
		JoinCommunityRequestDto requestDto = new JoinCommunityRequestDto("댓글", true, joinUserId);

		//when
		doNothing().when(communityService).joinCommunity(communityId, requestDto, userEmail);
		//then
		doPostThenOk("/api/community/participation/" + communityId, requestDto);
	}

	@DisplayName("나의ZIP참여목록_테스트")
	@Test
	public void testGetMyZipJoinList() throws Exception {
		//given
		User writerUser = User.builder().id(1L).name("작성자").build();
		User writerUser2 = User.builder().id(3L).name("작성자2").build();
		User joinUser = User.builder().id(2L).name("참여자").build();
		Community writeCommunity = Community.builder()
			.id(1L)
			.communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
			.title("제목1")
			.content("내용")
			.user(writerUser)
			.build();
		Community otherCommunity = Community.builder()
			.id(2L)
			.communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
			.title("제목2")
			.content("내용2")
			.user(writerUser2)
			.build();
		Participation participation = Participation.builder().id(1L).community(otherCommunity).user(joinUser).build();
		ParticipationResDto participationResDto = new ParticipationResDto(participation);
		WriteOutResDto writeOutResDto = new WriteOutResDto(writeCommunity);

		List<ParticipationResDto> participationResDtoList = new ArrayList<>();
		participationResDtoList.add(participationResDto);

		List<WriteOutResDto> writeOutResDtoList = new ArrayList<>();
		writeOutResDtoList.add(writeOutResDto);

		MyZipJoinResDto resultResDto = new MyZipJoinResDto(participationResDtoList, writeOutResDtoList);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		given(communityService.getJoinList(userEmail, "NEIGHBORHOODFRIEND")).willReturn(resultResDto);

		//when
		//then
		doGet("/api/community/participation", params);
	}

	@DisplayName("참여취소하기")
	@Test
	public void cancelParticipation() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/participation/" + communityId;

		doNothing().when(communityService).cancelJoinCommunity(communityId, userEmail);

		//when
		//then
		doPut(url, null);
	}

	@DisplayName("댓글작성하기")
	@Test
	public void setComment() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/comment/" + communityId;

		WriteCommentRequestDto requestDto = new WriteCommentRequestDto("댓글1", true, null);
		doNothing().when(communityService).saveComment(communityId, requestDto, userEmail);

		//when
		//then
		doPostThenOk(url, requestDto);
	}

	@DisplayName("대댓글작성하기")
	@Test
	public void setSubComment() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/comment/" + communityId;

		WriteCommentRequestDto requestDto = new WriteCommentRequestDto("댓글1", true, 1L);
		doNothing().when(communityService).saveComment(communityId, requestDto, userEmail);
		//when
		//then
		doPostThenOk(url, requestDto);
	}

	@DisplayName("수정하기테스트")
	@Test
	public void communityUpdateTest() throws Exception {
		//given
		long communityId = 1L;
		UpdateCommunityReqDto updateCommunityReqDto = new UpdateCommunityReqDto("수정된 제목", "수정된 내용", "수정된 제품명", 10000, "수정된 구매장소",
			"수정된 공유방법", 3, "수정된 설명");
		String url = "/api/community/" + communityId;

		doNothing().when(communityService).updateCommunity(communityId, updateCommunityReqDto);

		//when
		//then
		doPut(url, updateCommunityReqDto);
	}

	@DisplayName("커뮤니티 불러오기 테스트")
	@Test
	public void getCommunity() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		given(communityService.getCommunity(any(Long.class))).willReturn(any(CommunityResponseDto.class));

		doGet("/api/community/1", params);
	}

	@DisplayName("커뮤니티 목록 불러오기 테스트")
	@Test
	public void getCommunityList() throws Exception {
		List<CommunityResponseDto> communityResponseDtoList = new ArrayList<>();
		given(communityService.getCommunityList(any(String.class), anyString(), any()))
			.willReturn(new PageImpl<>(communityResponseDtoList));

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		doGet("/api/community", params);
	}
}

