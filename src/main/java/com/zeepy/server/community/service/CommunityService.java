package com.zeepy.server.community.service;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.AlreadyParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.*;
import com.zeepy.server.community.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(SaveCommunityRequestDto requestDto) {
        Long writerId = requestDto.getWriterId();
        User writer = userRepository.findById(writerId).orElseThrow(NotFoundUserException::new);
        requestDto.setUser(writer);
        Community communityToSave = requestDto.toEntity();
        Community community = communityRepository.save(communityToSave);

        ParticipationDto participationDto = new ParticipationDto(community, writer);
        Participation participationToSave = participationDto.toEntity();
        participationRepository.save(participationToSave);

        return community.getId();
    }

    @Transactional
    public void joinCommunity(Long id, JoinCommunityRequestDto joinCommunityRequestDto) {
        Community community = communityRepository.findById(id).orElseThrow(NotFoundCommunityException::new);

        Long participationUserId = joinCommunityRequestDto.getParticipationUserId();
        User participants = userRepository.findById(participationUserId).orElseThrow(NotFoundUserException::new);

        participationRepository.findByCommunityIdAndUserId(id, participationUserId).ifPresent(v -> {
            throw new AlreadyParticipationException();
        });

        ParticipationDto participationDto = new ParticipationDto(community, participants);
        Participation participationToSave = participationDto.toUpdateEntity();
        participationRepository.save(participationToSave);

        CommentDto commentDto = new CommentDto(joinCommunityRequestDto.getComment(), null, community, participants);
        commentRepository.save(commentDto.toEntity());
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

    @Transactional
    public void saveComment(Long communityId, WriteCommentRequestDto writeCommentRequestDto) {
        Long writerUserId = writeCommentRequestDto.getWriteUserId();
        Long superCommentId = writeCommentRequestDto.getSuperCommentId();
        User writer = userRepository.findById(writerUserId).orElseThrow(NotFoundUserException::new);
        Community community = communityRepository.findById(communityId).orElseThrow(NotFoundCommunityException::new);

        Comment superComment = commentRepository.findById(superCommentId).orElse(null);

        CommentDto commentDto = CommentDto.builder()
                .comment(writeCommentRequestDto.getComment())
                .superComment(superComment)
                .community(community)
                .writer(writer)
                .build();
        commentRepository.save(commentDto.toEntity());
    }

    @Transactional(readOnly = true)
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

