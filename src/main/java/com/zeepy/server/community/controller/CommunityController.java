package com.zeepy.server.community.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.zeepy.server.community.dto.CommunityLikeRequestDto;
import com.zeepy.server.community.dto.CommunityResponseDtos;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipJoinResDto;
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
	public ResponseEntity<Void> saveCommunity(@Valid @RequestBody SaveCommunityRequestDto saveCommunityRequestDto,
		@AuthenticationPrincipal String userEmail) {
		Long saveId = communityService.save(saveCommunityRequestDto, userEmail);
		return ResponseEntity.created(URI.create("/api/community/" + saveId)).build();
	}

	@PostMapping("/like")
	public ResponseEntity<Void> likeCommunity(@Valid @RequestBody CommunityLikeRequestDto communityLikeRequestDto
	) {
		Long likeId = communityService.like(communityLikeRequestDto);
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
		@Valid @RequestBody CommunityLikeRequestDto communityLikeRequestDto) {
		communityService.cancelLike(communityLikeRequestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/likes")
	public ResponseEntity<CommunityResponseDtos> getLikeList(@RequestParam Long id) {
		return new ResponseEntity<>(communityService.getLikeList(id), HttpStatus.OK);
	}

	@PostMapping("/comment/{id}")
	public ResponseEntity<Void> writeComment(
		@PathVariable("id") Long communityId,
		@Valid @RequestBody WriteCommentRequestDto writeCommentRequestDto,
		@AuthenticationPrincipal String userEmail
	) {
		communityService.saveComment(communityId, writeCommentRequestDto, userEmail);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/participation")
	public ResponseEntity<MyZipJoinResDto> getMyZipJoinList(@AuthenticationPrincipal String userEmail) {
		System.out.println("authenticationPrincipal : " + userEmail);
		MyZipJoinResDto myZipJoinList = communityService.getJoinList(userEmail);
		return ResponseEntity.ok().body(myZipJoinList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCommunity(
		@PathVariable("id") Long communityId,
		@Valid @RequestBody UpdateCommunityReqDto updateCommunityReqDto
	) {
		communityService.updateCommunity(communityId, updateCommunityReqDto);
		return ResponseEntity.ok().build();
	}
}
