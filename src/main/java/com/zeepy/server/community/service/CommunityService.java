package com.zeepy.server.community.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.AlreadyParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestCommentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.CommentDto;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipJoinResDto;
import com.zeepy.server.community.dto.ParticipationDto;
import com.zeepy.server.community.dto.ParticipationResDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.dto.UpdateCommunityReqDto;
import com.zeepy.server.community.dto.WriteCommentRequestDto;
import com.zeepy.server.community.dto.WriteOutResDto;
import com.zeepy.server.community.repository.CommentRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {
	private final CommunityRepository communityRepository;
	private final ParticipationRepository participationRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

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
}

