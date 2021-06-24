package com.zeepy.server.community.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.zeepy.server.common.ControllerTest;
import com.zeepy.server.common.config.security.CustomAccessDeniedHandler;
import com.zeepy.server.common.config.security.CustomAuthenticationEntryPoint;
import com.zeepy.server.common.config.security.JwtAuthenticationProvider;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.CancelJoinCommunityRequestDto;
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
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {CommunityController.class}, includeFilters = @ComponentScan.Filter(classes = {
	EnableWebSecurity.class}))
public class CommunityControllerTest extends ControllerTest {

	@MockBean
	private CommunityService communityService;

	@MockBean
	JwtAuthenticationProvider jwtAuthenticationProvider;
	@MockBean
	CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@MockBean
	CustomAccessDeniedHandler customAccessDeniedHandler;

	@Override
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		super.setUp(webApplicationContext);
	}

	@DisplayName("커뮤니티_등록_테스트")
	@Test
	@WithMockUser
	public void save() throws Exception {
		SaveCommunityRequestDto requestDto = SaveCommunityRequestDto.builder()
			.communityCategory(CommunityCategory.FREESHARING)
			.user(User.builder().id(1L).name("작성자").build())
			.title("강의 공동 구매해요!")
			.content("제곧내")
			.imageUrls(Arrays.asList("asdasd", "aaaaaaa", "ccccccccc"))
			.build();

		given(communityService.save(any(SaveCommunityRequestDto.class), any(String.class))).willReturn(1L);

		doPost("/api/community", requestDto);
	}

	@DisplayName("참가하기_테스트")
	@Test
	@WithMockUser("test@naver.com")
	public void joinCommunity() throws Exception {
		//given
		long communityId = 1L;
		String joinUserEmail = "test@naver.com";
		JoinCommunityRequestDto requestDto = new JoinCommunityRequestDto("aaaa", true);

		doNothing().when(communityService).joinCommunity(communityId, requestDto, joinUserEmail);
		//when
		//then
		doPost("/api/community/participation/" + communityId, requestDto);
	}

	@DisplayName("나의ZIP참여목록_테스트")
	@Test
	public void testGetMyZipJoinList() throws Exception {
		//given
		long joinUserId = 2L;
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
		given(communityService.getJoinList(joinUserId)).willReturn(resultResDto);

		//when
		//then
		doGet("/api/community/participation/1", params);
	}

	@DisplayName("참여취소하기")
	@Test
	public void cancelParticipation() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/participation/" + communityId;
		long userId = 2L;

		CancelJoinCommunityRequestDto requestDto = new CancelJoinCommunityRequestDto(userId);
		doNothing().when(communityService).cancelJoinCommunity(communityId, requestDto);

		//when
		//then
		doPut(url, requestDto);
	}

	@DisplayName("댓글작성하기")
	@Test
	public void setComment() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/comment/" + communityId;

		User commentUser = User.builder().id(2L).name("댓글작성자").build();

		WriteCommentRequestDto requestDto = new WriteCommentRequestDto("댓글1", true, null, commentUser.getId());
		doNothing().when(communityService).saveComment(communityId, requestDto);

		//when
		//then
		doPost(url, requestDto);
	}

	@DisplayName("대댓글작성하기")
	@Test
	public void setSubComment() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/comment/" + communityId;

		User commentUser = User.builder().id(2L).name("댓글작성자").build();

		WriteCommentRequestDto requestDto = new WriteCommentRequestDto("댓글1", true, 1L, commentUser.getId());
		doNothing().when(communityService).saveComment(communityId, requestDto);
		//when
		//then
		doPost(url, requestDto);
	}

	@DisplayName("수정하기테스트")
	@Test
	public void communityUpdateTest() throws Exception {
		//given
		long communityId = 1L;
		UpdateCommunityReqDto updateCommunityReqDto = new UpdateCommunityReqDto("수정된 제목", "수정된 제품명", 10000, "수정된 구매장소",
			"수정된 공유방법", 3, "수정된 설명");
		String url = "/api/community/" + communityId;

		doNothing().when(communityService).updateCommunity(communityId, updateCommunityReqDto);

		//when
		//then
		doPut(url, updateCommunityReqDto);
	}
}
