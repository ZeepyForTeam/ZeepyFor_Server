package com.zeepy.server.community.service;

import java.util.List;
import java.util.stream.Collectors;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.zeepy.server.community.domain.*;
import com.zeepy.server.push.util.FirebaseCloudMessageUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.AlreadyParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestCommentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.community.dto.CommentDto;
import com.zeepy.server.community.dto.CommunityLikeDto;
import com.zeepy.server.community.dto.CommunityLikeRequestDto;
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunityResponseDtos;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipJoinResDto;
import com.zeepy.server.community.dto.ParticipationDto;
import com.zeepy.server.community.dto.ParticipationResDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.dto.UpdateCommunityReqDto;
import com.zeepy.server.community.dto.WriteCommentRequestDto;
import com.zeepy.server.community.dto.WriteOutResDto;
import com.zeepy.server.community.repository.CommentRepository;
import com.zeepy.server.community.repository.CommunityLikeRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityLikeRepository communityLikeRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;
    private final CommentRepository commentRepository;

    // 파이어베이스 FCM 유틸리티 클래스
    private final FirebaseCloudMessageUtility firebaseCloudMessageUtility;

    @Transactional
    public Long like(CommunityLikeRequestDto requestDto) {
        Community community = communityRepository.findById(requestDto.getCommunityId())
                .orElseThrow(NotFoundCommunityException::new);

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(NotFoundUserException::new);

        CommunityLikeDto communityLikeDto = new CommunityLikeDto(user, community);
        CommunityLike communityLike = communityLikeRepository.save(communityLikeDto.toEntity());

        return communityLike.getId();
    }

    @Transactional
    public void cancelLike(CommunityLikeRequestDto requestDto) {
        Community community = communityRepository.findById(requestDto.getCommunityId())
                .orElseThrow(NotFoundCommunityException::new);

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(NotFoundUserException::new);

        CommunityLike communityLike = communityLikeRepository.findByCommunityAndUser(community, user);
        communityLikeRepository.deleteById(communityLike.getId());
    }

    @Transactional(readOnly = true)
    public CommunityResponseDtos getLikeList(Long id) {
        List<CommunityLike> communityLikeList = communityLikeRepository.findAllByUserId(id);
        return new CommunityResponseDtos(communityLikeList.stream()
                .map(CommunityLike::getCommunity)
                .map(CommunityResponseDto::new)
                .collect(Collectors.toList()));
    }

    @Transactional
    public Long save(SaveCommunityRequestDto requestDto, String userEmail) {
        User writer = getUserByEmail(userEmail);
        requestDto.setUser(writer);

        Community communityToSave = requestDto.toEntity();
        Community community = communityRepository.save(communityToSave);

        ParticipationDto participationDto = new ParticipationDto(community, writer);
        Participation participationToSave = participationDto.toEntity();
        participationRepository.save(participationToSave);

        return community.getId();
    }

    @Transactional
    public void joinCommunity(Long communityId, JoinCommunityRequestDto joinCommunityRequestDto, String userEmail) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);

        User participants = getUserByEmail(userEmail);

        participationRepository.findByCommunityIdAndUserId(communityId, participants
                .getId())
                .ifPresent(v -> {
                    throw new AlreadyParticipationException();
                });

        ParticipationDto participationDto = new ParticipationDto(community, participants);
        Participation participationToSave = participationDto.toUpdateEntity();
        participationRepository.save(participationToSave);

        String comment = joinCommunityRequestDto.getComment();
        Boolean isSecret = joinCommunityRequestDto.getIsSecret();
        CommentDto commentDto = new CommentDto(comment, isSecret, true, null, community, participants);
        commentRepository.save(commentDto.toEntity());

        /**
         * PUSH 알림 추가 로직
         * Case1
         * 누군가가 참여를 하였을 경우
         * Case2
         * 현재 참여자 수 == 최대 참여자 갯수
         * 커뮤니티 작성자에게 알림 전송
         * 공동목표 참여 인원에게 알림 전송
         */

        /**
         * Case. 1
         */
        firebaseCloudMessageUtility.sendTopicMessage(
                community.getUser()
                        .getId()
                        .toString(),
                "참여자 수가 변경되었어요.",
                makeMessageBodyAboutParticipationChanged(community));
        /**
         * Case. 2
         */
        if (participationToSave.getCommunity().checkCurrentNumberOfPeople()) {

            /**
             * 공동목표 제작자에게 알림
             */
            firebaseCloudMessageUtility.sendTopicMessage(
                    community.getUser()
                            .getId()
                            .toString(),
                    "목표가 달성되었어요.",
                    makeMessageBodyAboutParticipationComplete(community, true));

            /**
             * 참여 유저에게 알림
             */
            for (Participation participation : participationToSave.getCommunity().getParticipationsList()) {
                firebaseCloudMessageUtility.sendTopicMessage(
                        participation.getUser()
                                .getId()
                                .toString(),
                        "목표가 달성되었어요.",
                        makeMessageBodyAboutParticipationComplete(community, false));
            }
        }

    }

    @Transactional
    public void cancelJoinCommunity(Long communityId, String userEmail) {
        User findUser = getUserByEmail(userEmail);

        Community findCommunity = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);

        Long findUserId = findUser.getId();
        Long findCommunityId = findCommunity.getId();
        participationRepository.deleteByUserIdAndCommunityId(findUserId, findCommunityId);
        List<Comment> comments = commentRepository.findCommentsByUserIdAndCommunityId(findUserId, findCommunityId);

        Comment superComment = comments.stream().filter(v ->
                v.getSuperComment() == null &&
                        v.getIsParticipation())
                .findFirst()
                .orElseThrow(BadRequestCommentException::new);
        superComment.cancelParticipation();

        findCommunity.substractCurrentNumberOfPeople();

        /**
         * PUSH 알림 추가 로직
         * 누군가가 참여를 취소하였을 때
         */

        firebaseCloudMessageUtility.sendTopicMessage(
                findCommunity.getUser()
                        .getId()
                        .toString(),
                "참여자 수가 변경되었어요.",
                makeMessageBodyAboutParticipationChanged(findCommunity));
    }

    @Transactional
    public void saveComment(Long communityId, WriteCommentRequestDto writeCommentRequestDto, String userEmail) {
        Long superCommentId = writeCommentRequestDto.getSuperCommentId();
        User writer = getUserByEmail(userEmail);
        Community community = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);

        Comment superComment = null;

        if (superCommentId != null) {
            superComment = commentRepository.findById(superCommentId)
                    .orElseThrow(BadRequestCommentException::new);
        }

        String comment = writeCommentRequestDto.getComment();
        Boolean isSecret = writeCommentRequestDto.getIsSecret();
        CommentDto commentDto = CommentDto.builder()
                .comment(comment)
                .isSecret(isSecret)
                .superComment(superComment)
                .community(community)
                .writer(writer)
                .build();
        commentRepository.save(commentDto
                .toEntity());

        /**
         * PUSH 알림 추가 로직
         * superComment == null
         * 댓글 -> Community 제작자에게 PUSH 알림 전송
         * superComment != null
         * 대댓글 -> superComment 제작자에게 PUSH 알림 전송
         */

        if (superComment != null) {
            firebaseCloudMessageUtility.sendTopicMessage(
                    superCommentId.toString(),
                    "새로운 댓글이 달렸어요.",
                    makeMessageBodyAboutComment(community.getTitle(), superComment));
        } else {
            Long communityUserId = community.getUser().getId();
            firebaseCloudMessageUtility.sendTopicMessage(
                    communityUserId.toString(),
                    "새로운 대댓글이 달렸어요.",
                    makeMessageBodyAboutComment(community.getTitle(), superComment));
        }
    }

    @Transactional(readOnly = true)
    public MyZipJoinResDto getJoinList(String userEmail) {
        User findUser = getUserByEmail(userEmail);
        List<Participation> participationList = participationRepository.findAllByUserId(findUser
                .getId());
        List<Community> communityList = communityRepository.findAllByUserId(findUser
                .getId());

        List<ParticipationResDto> participationResDtoList = participationList.stream()
                .map(ParticipationResDto::new)
                .collect(Collectors.toList());
        List<WriteOutResDto> writeOutResDtoList = communityList.stream()
                .map(WriteOutResDto::new)
                .collect(Collectors.toList());

        return new MyZipJoinResDto(participationResDtoList, writeOutResDtoList);
    }

    @Transactional
    public void updateCommunity(Long communityId, UpdateCommunityReqDto updateCommunityReqDto) {
        Community findCommunity = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);
        updateCommunityReqDto.updateCommunity(findCommunity);
    }

    private User getUserByEmail(String authUserEmail) {
        return userRepository.findByEmail(authUserEmail)
                .orElseThrow(NotFoundUserException::new);
    }

    private String makeMessageBodyAboutParticipationComplete(Community community, Boolean isWriter) {
        String firstHeadString = "참여하신 ";
        if (isWriter) {
            firstHeadString = "작성하신 ";
        }

        if (community.getCommunityCategory() == CommunityCategory.FREESHARING) {
            return firstHeadString + "[" + community.getTitle() + "]" + " 무료나눔이 목표인원을 달성했어요!";
        } else if (community.getCommunityCategory() == CommunityCategory.NEIGHBORHOODFRIEND) {
            return firstHeadString + "[" + community.getTitle() + "]" + " 가 목표인원을 달성했어요!";
        }
        return firstHeadString + "[" + community.getTitle() + "]" + " 공동구매가 목표인원을 달성했어요!";
    }

    private String makeMessageBodyAboutParticipationChanged(Community community) {
        if (community.getCommunityCategory() == CommunityCategory.FREESHARING) {
            return "[" + community.getTitle() + "]" + " 무료나눔의 참여자 수가 변경되었어요.";
        } else if (community.getCommunityCategory() == CommunityCategory.NEIGHBORHOODFRIEND) {
            return "[" + community.getTitle() + "]" + " 의 참여자 수가 변경되었어요.";
        }
        return "[" + community.getTitle() + "]" + " 공동구매의 참여자 수가 변경되었어요.";
    }

    private String makeMessageBodyAboutComment(String title, Comment superComment) {
        if (superComment == null) {
            return "[" + title + "]" + " 글에 새로운 댓글이 달렸어요!";
        }
        return "[" + title + "]" + " 글에 남긴 댓글에 누군가 대댓글을 작성했어요.";
    }
}

