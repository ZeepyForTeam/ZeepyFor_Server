package com.zeepy.server.community.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.zeepy.server.common.job.AsyncJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.AlreadyParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestCommentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.JointPurchaseOwner;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.MoreThanOneParticipantException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.CommentDto;
import com.zeepy.server.community.dto.CommunityLikeDto;
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunitySimpleResDto;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipResponseDto;
import com.zeepy.server.community.dto.ParticipationDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.dto.UpdateCommunityReqDto;
import com.zeepy.server.community.dto.WriteCommentRequestDto;
import com.zeepy.server.community.repository.CommentRepository;
import com.zeepy.server.community.repository.CommunityLikeRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.push.util.FirebaseCloudMessageUtility;
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

    // ?????????????????? FCM ???????????? ?????????
    private final FirebaseCloudMessageUtility firebaseCloudMessageUtility;
    private final AsyncJob asyncJob;

    @Transactional
    public Long like(Long communityId, String userEmail) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);

        User user = getUserByEmail(userEmail);

        CommunityLikeDto communityLikeDto = new CommunityLikeDto(user, community);
        CommunityLike communityLike = communityLikeRepository.save(communityLikeDto.toEntity());

        return communityLike.getId();
    }

    @Transactional
    public void cancelLike(Long communityId, String userEmail) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);

        User user = getUserByEmail(userEmail);

        CommunityLike communityLike = communityLikeRepository.findByCommunityAndUser(community, user);
        communityLikeRepository.deleteById(communityLike.getId());
    }

    @Transactional
    public Long save(SaveCommunityRequestDto requestDto, String userEmail) {
        User writer = getUserByEmail(userEmail);
        Community communityToSave = requestDto.toEntity();
        communityToSave.setUser(writer);

        if (requestDto.getCommunityCategory().equals("JOINTPURCHASE")) {
            ParticipationDto participationDto = new ParticipationDto(communityToSave, writer);
            Participation participationToSave = participationDto.toUpdateEntity();
            participationRepository.save(participationToSave);
        }

        return communityRepository.save(communityToSave).getId();
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
         * PUSH ?????? ?????? ??????
         * Case1
         * ???????????? ????????? ????????? ??????
         * Case2
         * ?????? ????????? ??? == ?????? ????????? ??????
         * ???????????? ??????????????? ?????? ??????
         * ???????????? ?????? ???????????? ?????? ??????
         */

        /**
         * Case. 1
         */

        asyncJob.onStart(() -> {
            firebaseCloudMessageUtility.sendTopicMessage(
                    community.getUser()
                            .getId()
                            .toString(),
                    "????????? ?????? ??????????????????.",
                    makeMessageBodyAboutParticipationChanged(community));
        });

        /**
         * Case. 2
         */

        if (participationToSave.getCommunity().checkCurrentNumberOfPeople()) {

            /**
             * ?????? ???????????? ??????
             */

            List<Participation> participationList = participationToSave.getCommunity()
                    .getParticipationsList()
                    .stream()
                    .distinct()
                    .collect(Collectors.toList()); // ????????? ??????

            for (Participation participation : participationList) {
                asyncJob.onStart(() -> {
                    firebaseCloudMessageUtility.sendTopicMessage(
                            participation.getUser()
                                    .getId()
                                    .toString(),
                            "????????? ??????????????????.",
                            makeMessageBodyAboutParticipationComplete(community, false));
                });
            }

        }
    }

    @Transactional
    public void cancelJoinCommunity(Long communityId, String userEmail) {
        User findUser = getUserByEmail(userEmail);

        Community findCommunity = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);

        if (findUser.equals(findCommunity.getUser())) {
            throw new JointPurchaseOwner();
        }

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
         * PUSH ?????? ?????? ??????
         * ???????????? ????????? ??????????????? ???
         */

        asyncJob.onStart(() -> {
            firebaseCloudMessageUtility.sendTopicMessage(
                    findCommunity.getUser()
                            .getId()
                            .toString(),
                    "????????? ?????? ??????????????????.",
                    makeMessageBodyAboutParticipationChanged(findCommunity));
        });

    }

    @Transactional
    public Long saveComment(Long communityId, WriteCommentRequestDto writeCommentRequestDto, String userEmail) {
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

        /**
         * PUSH ?????? ?????? ??????
         * superComment == null
         * ?????? -> Community ??????????????? PUSH ?????? ??????
         * superComment != null
         * ?????????
         * -> Community ??????????????? PUSH ?????? ??????
         * -> superComment ??????????????? PUSH ?????? ??????
         */

        Comment finalSuperComment = superComment;

        asyncJob.onStart(() -> {
            Long communityUserId = community.getUser().getId();

            if (finalSuperComment != null) {
                firebaseCloudMessageUtility.sendTopicMessage(
                        superCommentId.toString(),
                        "????????? ???????????? ????????????.",
                        makeMessageBodyAboutComment(community.getTitle(), finalSuperComment));
                firebaseCloudMessageUtility.sendTopicMessage(
                        communityUserId.toString(),
                        "????????? ???????????? ????????????.",
                        makeMessageBodyAboutComment(community.getTitle(), finalSuperComment));
            } else {
                firebaseCloudMessageUtility.sendTopicMessage(
                        communityUserId.toString(),
                        "????????? ????????? ????????????.",
                        makeMessageBodyAboutComment(community.getTitle(), finalSuperComment));
            }
        });


        return commentRepository.save(commentDto
                .toEntity()).getId();

    }

    @Transactional(readOnly = true)
    public MyZipResponseDto getMyZipList(String userEmail, String communityCategory) {
        User findUser = getUserByEmail(userEmail);
        List<Participation> participationList = participationRepository.findAllByUserId(findUser.getId());
        List<Community> communityList = communityRepository.findAllByUserId(findUser.getId());
        List<CommunityLike> likeList = communityLikeRepository.findAllByUserId(findUser.getId());

        List<Community> participatedCommunities = participationList.stream()
                .map(Participation::getCommunity)
                .collect(Collectors.toList());
        List<Community> likedCommunities = likeList.stream()
                .map(CommunityLike::getCommunity)
                .collect(Collectors.toList());

        MyZipResponseDto myZipResponseDto = new MyZipResponseDto();
        myZipResponseDto.addCommunities(participatedCommunities);
        myZipResponseDto.addCommunities(communityList);
        myZipResponseDto.addCommunities(likedCommunities);

        if (!(communityCategory == null || communityCategory.isEmpty())) {
            myZipResponseDto.filtering(CommunityCategory.valueOf(communityCategory));
        }

        return myZipResponseDto;
    }

    @Transactional
    public void updateCommunity(Long communityId, UpdateCommunityReqDto updateCommunityReqDto) {
        Community findCommunity = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);
        if (findCommunity.getCurrentNumberOfPeople() > 1) {
            throw new MoreThanOneParticipantException();
        }
        updateCommunityReqDto.updateCommunity(findCommunity);
    }

    @Transactional(readOnly = true)
    public CommunityResponseDto getCommunity(Long communityId, String userEmail) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);
        User user = getUserByEmail(userEmail);

        CommunityResponseDto responseDto = CommunityResponseDto.of(community);

        Boolean isLiked = community.getLikes().stream()
                .map(CommunityLike::getUser)
                .anyMatch(l -> l.equals(user));
        responseDto.setLiked(isLiked);

        Boolean isParticipant = community.getParticipationsList().stream()
                .map(Participation::getUser)
                .anyMatch(p -> p.equals(user));
        responseDto.setParticipant(isParticipant);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public Page<CommunitySimpleResDto> getCommunityList(String address, String communityType, Pageable pageable) {
        Page<Community> communityList;

        if (address == null || address.isEmpty()) {
            if (communityType == null || communityType.isEmpty()) {
                communityList = communityRepository.findAll(pageable);
            } else {
                communityList = communityRepository.findByCommunityCategory(CommunityCategory.valueOf(communityType),
                        pageable);
            }
        } else {
            if (communityType == null || communityType.isEmpty()) {
                communityList = communityRepository.findByAddress(address, pageable);
            } else {
                communityList = communityRepository.findByAddressAndCommunityCategory(address,
                        CommunityCategory.valueOf(communityType), pageable);
            }
        }

        return new PageImpl<>(
                CommunitySimpleResDto.listOf(communityList.getContent()),
                pageable,
                communityList.getTotalElements());
    }

    private User getUserByEmail(String authUserEmail) {
        return userRepository.findByEmail(authUserEmail)
                .orElseThrow(NotFoundUserException::new);
    }

    private String makeMessageBodyAboutParticipationComplete(Community community, Boolean isWriter) {
        String firstHeadString = "???????????? ";
        if (isWriter) {
            firstHeadString = "???????????? ";
        }

        if (community.getCommunityCategory() == CommunityCategory.FREESHARING) {
            return firstHeadString + "[" + community.getTitle() + "]" + " ??????????????? ??????????????? ???????????????!";
        } else if (community.getCommunityCategory() == CommunityCategory.NEIGHBORHOODFRIEND) {
            return firstHeadString + "[" + community.getTitle() + "]" + " ??? ??????????????? ???????????????!";
        }
        return firstHeadString + "[" + community.getTitle() + "]" + " ??????????????? ??????????????? ???????????????!";
    }

    private String makeMessageBodyAboutParticipationChanged(Community community) {
        if (community.getCommunityCategory() == CommunityCategory.FREESHARING) {
            return "[" + community.getTitle() + "]" + " ??????????????? ????????? ?????? ??????????????????.";
        } else if (community.getCommunityCategory() == CommunityCategory.NEIGHBORHOODFRIEND) {
            return "[" + community.getTitle() + "]" + " ??? ????????? ?????? ??????????????????.";
        }
        return "[" + community.getTitle() + "]" + " ??????????????? ????????? ?????? ??????????????????.";
    }

    private String makeMessageBodyAboutComment(String title, Comment superComment) {
        if (superComment == null) {
            return "[" + title + "]" + " ?????? ????????? ????????? ????????????!";
        }
        return "[" + title + "]" + " ?????? ?????? ????????? ????????? ???????????? ???????????????.";
    }

    public void deleteCommunity(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(NotFoundCommunityException::new);
        if (community.getCurrentNumberOfPeople() > 1) {
            throw new MoreThanOneParticipantException();
        }

        communityRepository.deleteById(communityId);
    }
}

