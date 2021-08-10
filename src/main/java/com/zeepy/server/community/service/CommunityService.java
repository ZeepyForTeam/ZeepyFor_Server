package com.zeepy.server.community.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.AlreadyParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestCommentException;
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
import com.zeepy.server.community.dto.CommunityLikeResDto;
import com.zeepy.server.community.dto.CommunityLikeResDtos;
import com.zeepy.server.community.dto.CommunityResponseDto;
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

	@Transactional(readOnly = true)
	public CommunityLikeResDtos getLikeList(String userEmail, String communityCategory) {
		User user = getUserByEmail(userEmail);
		List<CommunityLike> communityLikeList = communityLikeRepository.findAllByUserId(user.getId());

		if (communityCategory == null || communityCategory.isEmpty()) {
			return communityLikeList.stream()
				.map(CommunityLikeResDto::new)
				.collect(Collectors.collectingAndThen(Collectors.toList(), CommunityLikeResDtos::new));
		}

		return communityLikeList.stream()
			.map(CommunityLikeResDto::new)
			.filter(c -> c.getCommunityCategory().equals(CommunityCategory.valueOf(communityCategory)))
			.collect(Collectors.collectingAndThen(Collectors.toList(), CommunityLikeResDtos::new));
	}

	@Transactional
	public Long save(SaveCommunityRequestDto requestDto, String userEmail) {
		User writer = getUserByEmail(userEmail);

		Community communityToSave = requestDto.toEntity();
		communityToSave.setUser(writer);
		Community community = communityRepository.save(communityToSave);

		if (requestDto.getCommunityCategory().equals("JOINTPURCHASE")) {
			ParticipationDto participationDto = new ParticipationDto(community, writer);
			Participation participationToSave = participationDto.toEntity();
			participationRepository.save(participationToSave);
		}

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
		commentRepository.save(commentDto.toEntity());
	}

	@Transactional(readOnly = true)
	public MyZipJoinResDto getJoinList(String userEmail, String communityCategory) {
		User findUser = getUserByEmail(userEmail);
		List<Participation> participationList = participationRepository.findAllByUserId(findUser
			.getId());
		List<Community> communityList = communityRepository.findAllByUserId(findUser
			.getId());

		if (communityCategory == null || communityCategory.isEmpty()) {
			List<ParticipationResDto> participationResDtoList = participationList.stream()
				.map(ParticipationResDto::new)
				.collect(Collectors.toList());
			List<WriteOutResDto> writeOutResDtoList = communityList.stream()
				.map(WriteOutResDto::new)
				.collect(Collectors.toList());
			return new MyZipJoinResDto(participationResDtoList, writeOutResDtoList);
		}

		List<ParticipationResDto> participationResDtoList = participationList.stream()
			.map(ParticipationResDto::new)
			.filter(c -> c.getCommunityCategory().equals(CommunityCategory.valueOf(communityCategory)))
			.collect(Collectors.toList());

		List<WriteOutResDto> writeOutResDtoList = communityList.stream()
			.map(WriteOutResDto::new)
			.filter(c -> c.getCommunityCategory().equals(CommunityCategory.valueOf(communityCategory)))
			.collect(Collectors.toList());

		return new MyZipJoinResDto(participationResDtoList, writeOutResDtoList);
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

	private User getUserByEmail(String authUserEmail) {
		return userRepository.findByEmail(authUserEmail)
			.orElseThrow(NotFoundUserException::new);
	}

	@Transactional(readOnly = true)
	public CommunityResponseDto getCommunity(Long communityId, String userEmail) {
		Community community = communityRepository.findById(communityId)
			.orElseThrow(NotFoundCommunityException::new);
		User user = getUserByEmail(userEmail);

		CommunityResponseDto responseDto = CommunityResponseDto.of(community);

		Boolean isLiked = community.getLikes().stream()
			.anyMatch(l -> l.getUser().equals(user));
		responseDto.setLiked(isLiked);

		Boolean isParticipant = community.getParticipationsList().stream()
			.anyMatch(p -> p.getUser().equals(user));
		responseDto.setParticipant(isParticipant);

		return CommunityResponseDto.of(community);
	}

	@Transactional(readOnly = true)
	public Page<CommunityResponseDto> getCommunityList(String address, String communityType, Pageable pageable) {
		Page<Community> communityList;

		if (address == null || address.isEmpty()) {
			if (communityType == null || communityType.isEmpty()) {
				communityList = communityRepository.findAll(pageable);
			}
			else {
				communityList = communityRepository.findByCommunityCategory(CommunityCategory.valueOf(communityType),
					pageable);
			}
		}

		else {
			if(communityType == null || communityType.isEmpty()) {
				communityList = communityRepository.findByAddress(address, pageable);
			}
			else {
				communityList = communityRepository.findByAddressAndCommunityCategory(address,
					CommunityCategory.valueOf(communityType), pageable);
			}
		}

		return new PageImpl<CommunityResponseDto>(
			CommunityResponseDto.listOf(communityList.getContent()),
			pageable,
			communityList.getTotalElements());
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

