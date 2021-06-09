package com.zeepy.server.building.controller;

import com.zeepy.server.building.dto.BuildingLikeRequestDto;
import com.zeepy.server.building.dto.BuildingLikeResponseDto;
import com.zeepy.server.building.service.BuildingLikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by Minky on 2021-06-02
 */

@RestController
@RequestMapping("/api/likes/buildings")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingLikeController {
    private final BuildingLikeService buildingLikeService;


    @GetMapping
    public ResponseEntity<List<BuildingLikeResponseDto>> getBuildingLikes() {
        return ResponseEntity.ok(buildingLikeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingLikeResponseDto> getBuildingLike(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(buildingLikeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> uploadBuildingLike(
            @Valid @RequestBody BuildingLikeRequestDto buildingLikeRequestDto
    ) {
        Long id = buildingLikeService.create(buildingLikeRequestDto);
        return ResponseEntity.created(URI.create("/api/likes/buildings/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBuildingLike(
            @PathVariable Long id,
            @Valid @RequestBody BuildingLikeRequestDto buildingLikeRequestDto
    ) {
        buildingLikeService.update(id, buildingLikeRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuildingLike(
            @PathVariable Long id
    ) {
        buildingLikeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
