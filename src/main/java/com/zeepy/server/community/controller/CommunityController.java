package com.zeepy.server.community.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.service.CommunityService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/community")
@RequiredArgsConstructor
@RestController
public class CommunityController {
	private final CommunityService communityService;

	@PostMapping
	public ResponseEntity<Void> saveCommunity(@Valid @RequestBody SaveCommunityRequestDto saveCommunityRequestDto) {
		Long saveId = communityService.save(saveCommunityRequestDto);
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
		@Valid @RequestBody JoinCommunityRequestDto joinCommunityRequestDto
	) {
		communityService.joinCommunity(communityId, joinCommunityRequestDto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/participation/{id}")
	public ResponseEntity<Void> cancelJoinCommunity(
		@PathVariable("id") Long communityId,
		@Valid @RequestBody CancelJoinCommunityRequestDto cancelJoinCommunityRequestDto
	) {
		communityService.cancelJoinCommunity(communityId, cancelJoinCommunityRequestDto);
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
		return new ResponseEntity<CommunityResponseDtos>(communityService.getLikeList(id), HttpStatus.OK);
	}
	@PostMapping("/comment/{id}")
	public ResponseEntity<Void> writeComment(
		@PathVariable("id") Long communityId,
		@Valid @RequestBody WriteCommentRequestDto writeCommentRequestDto
	) {
		communityService.saveComment(communityId, writeCommentRequestDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/participation/{id}")
	public ResponseEntity<MyZipJoinResDto> getMyZipJoinList(@PathVariable("id") Long userId) {
		MyZipJoinResDto myZipJoinList = communityService.getJoinList(userId);
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
