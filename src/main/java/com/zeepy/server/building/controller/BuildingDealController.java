package com.zeepy.server.building.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.building.dto.BuildingBulkRequestDto;
import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.building.dto.BuildingDealResponseDto;
import com.zeepy.server.building.service.BuildingDealService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-05-15
 */

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingDealController {
    private final BuildingDealService buildingDealService;

    @GetMapping
    public ResponseEntity<List<BuildingDealResponseDto>> getBuildingDeals() {
		return ResponseEntity.ok(buildingDealService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BuildingDealResponseDto> getBuildingDeal(
		@PathVariable Long id
	) {
		return ResponseEntity.ok(buildingDealService.getById(id));
	}

	@GetMapping("/floors")
	public ResponseEntity<BuildingDealResponseDto> getBuildingDealByFloorAndBuildingId(
		@RequestParam("floor") int floor,
		@RequestParam("id") Long id
	) {
		return ResponseEntity.ok(buildingDealService.getByFloorAndBuildingId(floor, id));
	}

	@PostMapping
	public ResponseEntity<Void> uploadBuildingDeal(
		@Valid @RequestBody BuildingDealRequestDto buildingDealRequestDto
	) {
		Long id = buildingDealService.create(buildingDealRequestDto);
		return ResponseEntity.created(URI.create("/api/deals/" + id)).build();
	}

	@PostMapping("/batch")
	public ResponseEntity<Void> batchInsertBuilding(
		@Valid @RequestBody List<BuildingBulkRequestDto> buildingRequestDtoList
	) {
		buildingDealService.batchInsert(buildingRequestDtoList);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateBuildingDeal(
		@PathVariable Long id,
		@Valid @RequestBody BuildingDealRequestDto buildingDealRequestDto
	) {
		buildingDealService.update(id, buildingDealRequestDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBuildingDeal(
		@PathVariable Long id
	) {
		buildingDealService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
