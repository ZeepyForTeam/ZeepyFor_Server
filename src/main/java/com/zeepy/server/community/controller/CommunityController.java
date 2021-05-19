package com.zeepy.server.community.controller;

import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.ParticipationResDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
        Long participationId = communityService.joinCommunity(id, joinCommunityRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/participation/{id}")
    public ResponseEntity<List<ParticipationResDto>> getMyZipJoinList(@PathVariable("id") Long id) {
        List<ParticipationResDto> myZipJoinList = communityService.getJoinList(id);
        return ResponseEntity.ok().body(myZipJoinList);
    }
}
