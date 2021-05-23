package com.zeepy.server.building.controller;

import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.building.dto.BuildingDealResponseDto;
import com.zeepy.server.building.service.BuildingDealService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
            @RequestBody BuildingDealRequestDto buildingDealRequestDto
    ) {
        Long id = buildingDealService.create(buildingDealRequestDto);
        return ResponseEntity.created(URI.create("/api/deals/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBuildingDeal(
            @PathVariable Long id,
            @RequestBody BuildingDealRequestDto buildingDealRequestDto
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