package com.zeepy.server.community.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.community.dto.CommunityLikeRequestDto;
import com.zeepy.server.community.dto.CommunityResponseDtos;
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
}
