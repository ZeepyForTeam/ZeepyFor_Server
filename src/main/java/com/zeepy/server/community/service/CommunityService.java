package com.zeepy.server.community.service;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    @Transactional
    public Long save(SaveCommunityRequestDto requestDto) {
        Community community = communityRepository.save(requestDto.toEntity());
        return community.getId();
    }
}

