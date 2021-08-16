package com.zeepy.server.building.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.domain.BuildingLike;
import com.zeepy.server.building.dto.BuildingLikeRequestDto;
import com.zeepy.server.building.dto.BuildingLikeResponseDto;
import com.zeepy.server.building.repository.BuildingLikeRepository;
import com.zeepy.server.building.repository.BuildingRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-06-02
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingLikeService {
	private final BuildingLikeRepository buildingLikeRepository;
	private final BuildingRepository buildingRepository;
	private final UserRepository userRepository;

	// CREATE
	@Transactional
	public Long create(BuildingLikeRequestDto buildingLikeRequestDto, String userEmail) {
		Building building = buildingRepository
			.findById(buildingLikeRequestDto.getBuildingId())
			.orElseThrow(NoContentException::new);

		User user = userRepository
			.findByEmail(userEmail)
			.orElseThrow(NotFoundUserException::new);

		BuildingLike buildingLike = buildingLikeRequestDto.returnBuildingLikeEntity();
		buildingLike.setBuilding(building);
		buildingLike.setUser(user);

		return buildingLikeRepository.save(buildingLike)
			.getId();
	}

	// READ
	@Transactional(readOnly = true)
	public List<BuildingLikeResponseDto> getAll() {
		List<BuildingLike> buildingLikeList = buildingLikeRepository.findAll();
		return BuildingLikeResponseDto.listOf(buildingLikeList);
	}

	// READ
	@Transactional(readOnly = true)
	public BuildingLikeResponseDto getById(Long id) {
		BuildingLike buildingLike = getBuildingLikeById(id);
		return BuildingLikeResponseDto.of(buildingLike);
	}

	// UPDATE
	@Transactional
	public void update(Long id) {
		BuildingLike buildingLike = getBuildingLikeById(id);
		buildingLike.update();
		buildingLikeRepository.save(buildingLike);
	}

	// DELETE
	@Transactional
	public void deleteById(Long id) {
		buildingLikeRepository.deleteById(id);
	}

	private BuildingLike getBuildingLikeById(Long id) {
		return buildingLikeRepository
			.findById(id)
			.orElseThrow(NoContentException::new);
	}
}
