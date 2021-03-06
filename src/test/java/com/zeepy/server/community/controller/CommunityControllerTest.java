package com.zeepy.server.community.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunitySimpleResDto;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipResponseDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.dto.UpdateCommunityReqDto;
import com.zeepy.server.community.dto.WriteCommentRequestDto;
import com.zeepy.server.community.service.CommunityService;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.UserDto;

@DisplayName("????????????_????????????_?????????")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {CommunityController.class}, includeFilters = @ComponentScan.Filter(classes = {
	EnableWebSecurity.class}))
@MockBean(JpaMetamodelMappingContext.class)
public class CommunityControllerTest extends ControllerTest {
	@MockBean
	private CommunityService communityService;
	@MockBean
	CustomDetailsService customDetailsService;

	private final CommunityResponseDto communityResponseDto = new CommunityResponseDto(
		1L,
		CommunityCategory.JOINTPURCHASE,
		"??????",
		"???????????? ?????????",
		"1000???",
		"??????",
		"????????????????????? ?????????",
		2,
		5,
		"instructions",
		"?????????????????????",
		"??????",
		new UserDto(1L, "yen", "1111.png"),
		false,
		false,
		null,
		null,
		null,
		LocalDateTime.of(2021, 8, 24, 18, 52),
		false
	);

	private final String userEmail = "test@naver.com";

	@Override
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		super.setUp(webApplicationContext);
	}

	@DisplayName("????????????_??????_?????????")
	@Test
	@WithMockUser(username = "user", password = "123123", roles = "USER")
	public void save() throws Exception {
		SaveCommunityRequestDto requestDto = SaveCommunityRequestDto.builder()
			.address("??????")
			.communityCategory("JOINTPURCHASE")
			.title("?????? ?????? ????????????!")
			.content("?????????")
			.imageUrls(Arrays.asList("asdasd", "aaaaaaa", "ccccccccc"))
			.build();

		given(communityService.save(any(), any())).willReturn(1L);

		doPost("/api/community", requestDto);
	}

	@DisplayName("?????????_??????_?????????")
	@Test
	public void like() throws Exception {
		given(communityService.like(any(Long.class), any(String.class))).willReturn(1L);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("communityId", "1");
		params.add("userEmail", "hey@naver.com");
		doPostWithParams("/api/community/like", params);
	}

	@DisplayName("?????????_??????_?????????")
	@Test
	public void cancelLike() throws Exception {
		doNothing().when(communityService).cancelLike(1L, "hey@naver.com");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("communityId", "1");
		params.add("userEmail", "hey@naver.com");
		doDeleteWithParams("/api/community/like", params);
	}

	@DisplayName("????????????_?????????")
	@Test
	@WithMockUser("test@naver.com")
	public void joinCommunity() throws Exception {
		//given
		long communityId = 1L;
		long joinUserId = 2L;
		JoinCommunityRequestDto requestDto = new JoinCommunityRequestDto("??????", true);

		//when
		doNothing().when(communityService).joinCommunity(communityId, requestDto, userEmail);
		//then
		doPostThenOk("/api/community/participation/" + communityId, requestDto);
	}

	@DisplayName("??????ZIP????????????_?????????")
	@Test
	public void testGetMyZipJoinList() throws Exception {
		//given
		User writerUser = User.builder().id(1L).name("?????????").build();
		User writerUser2 = User.builder().id(3L).name("?????????2").build();
		User joinUser = User.builder().id(2L).name("?????????").build();
		Community writeCommunity = Community.builder()
			.id(1L)
			.communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
			.title("??????1")
			.content("??????")
			.user(writerUser)
			.build();
		Community otherCommunity = Community.builder()
			.id(2L)
			.communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
			.title("??????2")
			.content("??????2")
			.user(writerUser2)
			.build();
		Participation participation = Participation.builder().id(1L).community(otherCommunity).user(joinUser).build();

		MyZipResponseDto resultResDto = new MyZipResponseDto();
		resultResDto.addCommunities(Collections.singletonList(participation.getCommunity()));
		resultResDto.addCommunities(Collections.singletonList(writeCommunity));

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		given(communityService.getMyZipList(userEmail, "NEIGHBORHOODFRIEND")).willReturn(resultResDto);

		//when
		//then
		doGet("/api/community/myzip", params);
	}

	@DisplayName("??????????????????")
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

	@DisplayName("??????????????????")
	@Test
	public void setComment() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/comment/" + communityId;

		WriteCommentRequestDto requestDto = new WriteCommentRequestDto("??????1", true, null);
		given(communityService.saveComment(anyLong(), any(WriteCommentRequestDto.class), any())).willReturn(1L);

		//when
		//then
		doPost(url, requestDto);
	}

	@DisplayName("?????????????????????")
	@Test
	public void setSubComment() throws Exception {
		//given
		long communityId = 1L;
		String url = "/api/community/comment/" + communityId;

		WriteCommentRequestDto requestDto = new WriteCommentRequestDto("??????1", true, 1L);
		given(communityService.saveComment(anyLong(), any(WriteCommentRequestDto.class), any())).willReturn(1L);
		//when
		//then
		doPost(url, requestDto);
	}

	@DisplayName("?????????????????????")
	@Test
	public void communityUpdateTest() throws Exception {
		//given
		long communityId = 1L;
		UpdateCommunityReqDto updateCommunityReqDto = new UpdateCommunityReqDto("????????? ??????", "????????? ??????", "????????? ?????????",
			"????????? ??????", "????????? ????????????",
			"????????? ????????????", 3, "????????? ??????");
		String url = "/api/community/" + communityId;

		doNothing().when(communityService).updateCommunity(communityId, updateCommunityReqDto);

		//when
		//then
		doPut(url, updateCommunityReqDto);
	}

	@DisplayName("???????????? ???????????? ?????????")
	@Test
	public void getCommunity() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		given(communityService.getCommunity(eq(1L), eq(userEmail))).willReturn(communityResponseDto);

		doGet("/api/community/1", params);
	}

	@DisplayName("???????????? ?????? ???????????? ?????????")
	@Test
	public void getCommunityList() throws Exception {
		List<CommunitySimpleResDto> communityResponseDtoList = new ArrayList<>();
		given(communityService.getCommunityList(null, null, PageRequest.of(0, 2)))
			.willReturn(new PageImpl<>(communityResponseDtoList));

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		doGet("/api/community", params);
	}
}

