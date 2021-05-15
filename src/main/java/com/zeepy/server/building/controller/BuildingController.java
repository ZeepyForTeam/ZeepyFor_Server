package com.zeepy.server.building.controller;

import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.service.BuildingService;
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
@RequestMapping("/api/building")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public ResponseEntity<List<BuildingResponseDto>> getBuildings() {
        return ResponseEntity.ok(buildingService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> uploadBuilding(
            @RequestBody BuildingRequestDto buildingRequestDto
    ) {
        Long id = buildingService.create(buildingRequestDto);
        return ResponseEntity.created(URI.create("api/building/" + id)).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateBuilding(
            @PathVariable Long id,
            @RequestBody BuildingRequestDto buildingRequestDto
    ) {
        buildingService.update(id, buildingRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBuilding(
            @PathVariable Long id
    ) {
        buildingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
