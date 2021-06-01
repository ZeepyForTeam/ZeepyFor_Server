package com.zeepy.server.community.controller;

import com.zeepy.server.community.dto.*;
import com.zeepy.server.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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
            @PathVariable("id") Long id,
            @Valid @RequestBody JoinCommunityRequestDto joinCommunityRequestDto
    ) {
        communityService.joinCommunity(id, joinCommunityRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/participation/{id}")
    public ResponseEntity<Void> cancelJoinCommunity(
            @PathVariable("id") Long id,
            @Valid @RequestBody CancelJoinCommunityRequestDto cancelJoinCommunityRequestDto
    ) {
        communityService.cancelJoinCommunity(id, cancelJoinCommunityRequestDto);
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
    public ResponseEntity<MyZipJoinResDto> getMyZipJoinList(@PathVariable("id") Long id) {
        MyZipJoinResDto myZipJoinList = communityService.getJoinList(id);
        return ResponseEntity.ok().body(myZipJoinList);
    }

    @GetMapping("/community")//삭제할거(테스트용)
    public ResponseEntity<CommunityListResDto> getCommunityList() {
        CommunityListResDto communityListResDto = communityService.getCommunities();
        return ResponseEntity.ok().body(communityListResDto);
    }
}
