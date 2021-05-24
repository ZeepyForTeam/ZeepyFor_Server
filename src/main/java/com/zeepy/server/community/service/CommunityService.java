package com.zeepy.server.community.service;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.*;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(SaveCommunityRequestDto requestDto) {
        Community communityToSave = requestDto.toEntity();
        Community community = communityRepository.save(communityToSave);

        return community.getId();
    }

    @Transactional
    public Long joinCommunity(Long id, JoinCommunityRequestDto joinCommunityRequestDto) {
        Community community = communityRepository.findById(id).orElseThrow(NotFoundCommunityException::new);
        if (joinCommunityRequestDto.isCommentExist()) {
            String commentToUpdate = joinCommunityRequestDto.getComment();
            community.update(commentToUpdate);
        }
        User user = userRepository.findById(joinCommunityRequestDto.getParticipationUserId()).orElseThrow(NotFoundUserException::new);

        ParticipationDto participationDto = new ParticipationDto(community, user);
        Participation participationToSave = participationDto.toEntity();
        Participation saveParticipation = participationRepository.save(participationToSave);

        return saveParticipation.getId();
    }

    @Transactional
    public void cancelJoinCommunity(Long id, CancelJoinCommunityRequestDto cancelJoinCommunityRequestDto) {
        Long cancelJoinUserId = cancelJoinCommunityRequestDto.getCancelUserId();

        User findUser = userRepository.findById(cancelJoinUserId).orElseThrow(NotFoundUserException::new);
        Community findCommunity = communityRepository.findById(id).orElseThrow(NotFoundCommunityException::new);

        Long findUserId = findUser.getId();
        Long findCommunityId = findCommunity.getId();
        participationRepository.deleteByUserIdAndCommunityId(findUserId, findCommunityId);
    }

    public MyZipJoinResDto getJoinList(Long id) {
        List<Participation> participationList = participationRepository.findAllByUserId(id);
        List<Community> communityList = communityRepository.findAllByUserId(id);

        List<ParticipationResDto> participationResDtoList = participationList.stream()
                .map(ParticipationResDto::new)
                .collect(Collectors.toList());
        List<WriteOutResDto> writeOutResDtoList = communityList.stream()
                .map(WriteOutResDto::new)
                .collect(Collectors.toList());

        return new MyZipJoinResDto(participationResDtoList, writeOutResDtoList);
    }
}

