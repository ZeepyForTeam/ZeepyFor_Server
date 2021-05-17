package com.zeepy.server.community.service;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.ParticipationDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(SaveCommunityRequestDto requestDto) {
        Community community = communityRepository.save(requestDto.toEntity());
        return community.getId();
    }

    @Transactional
    public Long joinCommunity(Long id, JoinCommunityRequestDto joinCommunityRequestDto) {
        Community community = communityRepository.findById(id).orElseThrow(RuntimeException::new);//커뮤니티없음 익셉션 해줘야함
        if (joinCommunityRequestDto.isCommentExist()) {
            community.update(joinCommunityRequestDto.getComment());
        }
        User user = userRepository.findById(joinCommunityRequestDto.getParticipationUserId()).orElseThrow(RuntimeException::new);//사용자없음 익셉션
        ParticipationDto participationDto = new ParticipationDto(community, user);
        return participationRepository.save(participationDto.toEntity()).getId();
    }
}

