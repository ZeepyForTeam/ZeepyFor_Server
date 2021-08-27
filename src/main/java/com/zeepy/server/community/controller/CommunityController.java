package com.zeepy.server.community.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunitySimpleResDto;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipResponseDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.dto.UpdateCommunityReqDto;
import com.zeepy.server.community.dto.WriteCommentRequestDto;
import com.zeepy.server.community.service.CommunityService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/community")
@RequiredArgsConstructor
@RestController
public class CommunityController {
	private final CommunityService communityService;

	@PostMapping
	public ResponseEntity<Void> saveCommunity(
		@Valid @RequestBody SaveCommunityRequestDto saveCommunityRequestDto,
		@AuthenticationPrincipal String userEmail) {
		Long saveId = communityService.save(saveCommunityRequestDto, userEmail);
		return ResponseEntity.created(URI.create("/api/community/" + saveId)).build();
	}

	@PostMapping("/like")
	public ResponseEntity<Void> likeCommunity(
		@RequestParam Long communityId,
		@AuthenticationPrincipal String userEmail
	) {
		Long likeId = communityService.like(communityId, userEmail);
		return ResponseEntity.created(URI.create("/api/community/like/" + likeId)).build();
	}

	@PostMapping("/participation/{id}")
	public ResponseEntity<Void> toJoinCommunity(
		@PathVariable("id") Long communityId,
		@Valid @RequestBody JoinCommunityRequestDto joinCommunityRequestDto,
		@AuthenticationPrincipal String userEmail
	) {
		communityService.joinCommunity(communityId, joinCommunityRequestDto, userEmail);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/participation/{id}")
	public ResponseEntity<Void> cancelJoinCommunity(
		@PathVariable("id") Long communityId,
		@AuthenticationPrincipal String userEmail
	) {
		communityService.cancelJoinCommunity(communityId, userEmail);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/like")
	public ResponseEntity<Void> cancelLikeCommunity(
		@RequestParam Long communityId,
		@AuthenticationPrincipal String userEmail) {
		communityService.cancelLike(communityId, userEmail);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/comment/{id}")
	public ResponseEntity<Void> writeComment(
		@PathVariable("id") Long communityId,
		@Valid @RequestBody WriteCommentRequestDto writeCommentRequestDto,
		@AuthenticationPrincipal String userEmail
	) {
		Long commentId = communityService.saveComment(communityId, writeCommentRequestDto, userEmail);
		return ResponseEntity.created(URI.create("/api/community/comment/" + communityId + "/" + commentId)).build();
	}

	@GetMapping("/myzip")
	public ResponseEntity<MyZipResponseDto> getMyZipList(
		@AuthenticationPrincipal String userEmail,
		@RequestParam(required = false) String communityCategory) {
		System.out.println("authenticationPrincipal : " + userEmail);
		MyZipResponseDto myZipList = communityService.getMyZipList(userEmail, communityCategory);
		return ResponseEntity.ok().body(myZipList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCommunity(
		@PathVariable("id") Long communityId,
		@Valid @RequestBody UpdateCommunityReqDto updateCommunityReqDto
	) {
		communityService.updateCommunity(communityId, updateCommunityReqDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CommunityResponseDto> getCommunity(
		@PathVariable("id") Long communityId,
		@AuthenticationPrincipal String userEmail) {
		CommunityResponseDto communityResponseDto = communityService.getCommunity(communityId, userEmail);
		return ResponseEntity.ok().body(communityResponseDto);
	}

	@GetMapping
	public ResponseEntity<Page<CommunitySimpleResDto>> getCommunityList(
		@RequestParam(required = false) String address,
		@RequestParam(required = false) String communityType,
		@PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
		return ResponseEntity.ok().body(communityService.getCommunityList(address, communityType, pageable));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCommunity(
		@PathVariable("id") Long communityId
	) {
		communityService.deleteCommunity(communityId);
		return ResponseEntity.noContent().build();
	}
}
