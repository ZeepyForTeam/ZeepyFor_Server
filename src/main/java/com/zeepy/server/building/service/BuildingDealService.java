package com.zeepy.server.building.service;

import java.util.ArrayList;
import java.util.List;

import com.zeepy.server.building.dto.BuildingBulkRequestDto;
import com.zeepy.server.building.dto.BuildingRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.building.domain.Building;
import com.zeepy.server.building.domain.BuildingDeal;
import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.building.dto.BuildingDealResponseDto;
import com.zeepy.server.building.repository.BuildingDealRepository;
import com.zeepy.server.building.repository.BuildingRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoContentException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-05-15
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingDealService {
	private final BuildingDealRepository buildingDealRepository;
	private final BuildingRepository buildingRepository;

	// CREATE
	@Transactional
	public Long create(BuildingDealRequestDto buildingDealRequestDto) {
		Building building = buildingRepository
			.findById(buildingDealRequestDto.getBuildingId())
			.orElseThrow(NoContentException::new);

		BuildingDeal buildingDeal = buildingDealRequestDto.returnBuildingDealEntity();
		buildingDeal.setBuilding(building);

		return buildingDealRepository.save(buildingDeal)
			.getId();
	}

	@Transactional
	public void batchInsert(List<BuildingBulkRequestDto> buildingDealRequestDtoList) {
		int batchCount = 0;
		List<BuildingDeal> buildingDealList = new ArrayList();
		for (BuildingBulkRequestDto buildingDealRequestDto : buildingDealRequestDtoList) {

			BuildingDeal buildingDeal = buildingDealRequestDto.returnBuildingDealEntity();
			Building building = buildingDealRequestDto.returnBuildingEntity();

			buildingDeal.setBuilding(building);
			buildingDealList.add(buildingDeal);

			batchCount += 1;
			if (batchCount % 100 == 0) {
				buildingDealRepository.saveAll(buildingDealList);
				buildingDealList = new ArrayList();
			}
		}
	}

	// READ
	@Transactional(readOnly = true)
	public List<BuildingDealResponseDto> getAll() {
		List<BuildingDeal> buildingDealList = buildingDealRepository.findAll();
		return BuildingDealResponseDto.listOf(buildingDealList);
	}

	// READ
	@Transactional(readOnly = true)
	public BuildingDealResponseDto getById(Long id) {
		BuildingDeal buildingDeal = getBuildingDealById(id);
		return BuildingDealResponseDto.of(buildingDeal);
	}

	// READ
	@Transactional(readOnly = true)
	public BuildingDealResponseDto getByFloorAndBuildingId(int floor, Long id) {
		BuildingDeal buildingDeal = buildingDealRepository
			.findByFloorAndBuilding_Id(floor, id)
			.orElseThrow(NoContentException::new);

		return BuildingDealResponseDto.of(buildingDeal);
	}

	// UPDATE
	@Transactional
	public void update(Long id, BuildingDealRequestDto buildingDealRequestDto) {
		BuildingDeal buildingDeal = getBuildingDealById(id);
		buildingDeal.update(buildingDealRequestDto);
		buildingDealRepository.save(buildingDeal);
	}

	// DELETE
	@Transactional
	public void deleteById(Long id) {
		buildingDealRepository.deleteById(id);
	}

	private BuildingDeal getBuildingDealById(Long id) {
		return buildingDealRepository
			.findById(id)
			.orElseThrow(NoContentException::new);
	}
}
