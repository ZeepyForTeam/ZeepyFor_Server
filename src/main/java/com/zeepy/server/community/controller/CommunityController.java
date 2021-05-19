package com.zeepy.server.community.controller;

import com.zeepy.server.community.dto.SaveCommunityRequestDto;
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


}
