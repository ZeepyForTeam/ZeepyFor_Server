package com.zeepy.server.building.controller;

import com.zeepy.server.building.dto.BuildingDealRequestDto;
import com.zeepy.server.building.service.BuildingDealService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Created by Minky on 2021-05-15
 */

@RestController
@RequestMapping("/api/deal")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingDealController {
    private final BuildingDealService buildingDealService;

    @PostMapping
    public ResponseEntity<Void> uploadBuildingDeal(
            @RequestBody BuildingDealRequestDto buildingDealRequestDto
    ) {
        Long id = buildingDealService.create(buildingDealRequestDto);
        return ResponseEntity.created(URI.create("api/deal/" + id)).build();
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
