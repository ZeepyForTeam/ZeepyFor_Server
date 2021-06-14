package com.zeepy.server.community.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.community.dto.CancelJoinCommunityRequestDto;
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
	public ResponseEntity<Void> saveCommunity(@Valid @RequestBody SaveCommunityRequestDto saveCommunityRequestDto) {
		Long saveId = communityService.save(saveCommunityRequestDto);
		return ResponseEntity.created(URI.create("/api/community/" + saveId)).build();
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
