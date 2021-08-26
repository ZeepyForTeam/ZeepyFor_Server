package com.zeepy.server.building.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.zeepy.server.review.domain.MultiChoiceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.building.domain.DealType;
import com.zeepy.server.building.dto.BuildingAutoCompleteResponseDto;
import com.zeepy.server.building.dto.BuildingRequestDto;
import com.zeepy.server.building.dto.BuildingResponseDto;
import com.zeepy.server.building.service.BuildingService;
import com.zeepy.server.review.domain.Furniture;
import com.zeepy.server.review.domain.RoomCount;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-05-15
 */

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping("/all")
    public ResponseEntity<List<BuildingResponseDto>> getAll() {
        return ResponseEntity.ok().body(buildingService.getAll());
    }

    @GetMapping
    public ResponseEntity<Page<BuildingResponseDto>> getBuildings(
            @RequestParam(value = "shortAddress", required = false) String shortAddress,
            @RequestParam(value = "geMonthly", required = false) Integer greaterMonthlyRent,
            @RequestParam(value = "leMonthly", required = false) Integer lesserMonthlyRent,
            @RequestParam(value = "geDeposit", required = false) Integer greaterDeposit,
            @RequestParam(value = "leDeposit", required = false) Integer lesserDeposit,
            @RequestParam(value = "neType", required = false) DealType notEqualDealType,
            @RequestParam(value = "inSoundInsulation", required = false) MultiChoiceReview soundInsulation,
            @RequestParam(value = "inPest", required = false) MultiChoiceReview pest,
            @RequestParam(value = "inLightning", required = false) MultiChoiceReview lightning,
            @RequestParam(value = "inWaterPressure", required = false) MultiChoiceReview waterPressure,
            @RequestParam(value = "inRoomCounts", required = false) List<RoomCount> roomCounts,
            @RequestParam(value = "inFurnitures", required = false) List<Furniture> furnitures,
            Pageable pageable
    ) {
        return ResponseEntity.ok(buildingService.getAll(
                shortAddress,
                greaterMonthlyRent,
                lesserMonthlyRent,
                greaterDeposit,
                lesserDeposit,
                notEqualDealType,
                soundInsulation,
                pest,
                lightning,
                waterPressure,
                roomCounts,
                furnitures,
                pageable
        ));
    }

    @GetMapping("/like")
    public ResponseEntity<Page<BuildingResponseDto>> getBuildingsUserLike(
            @AuthenticationPrincipal String userEmail,
            Pageable pageable
    ) {
        return ResponseEntity.ok(buildingService.getUserLike(
                userEmail,
                pageable
        ));
    }

    @GetMapping("/address")
    public ResponseEntity<BuildingResponseDto> getBuildingByAddress(
            @RequestParam("address") String address
    ) {
        return ResponseEntity.ok(buildingService.getByAddress(address));
    }

    @GetMapping("/addresses")
    public ResponseEntity<Page<BuildingAutoCompleteResponseDto>> getBuildingAddresses(
            @RequestParam("address") String address,
            Pageable pageable
    ) {
        return ResponseEntity.ok(buildingService.getBuildingAddressesByAddress(address, pageable));
    }

    @GetMapping("/location")
    public ResponseEntity<List<BuildingResponseDto>> getBuildingsByLocation(
            @RequestParam("latitudeGreater") double latitudeGreater,
            @RequestParam("latitudeLess") double latitudeLess,
            @RequestParam("longitudeGreater") double longitudeGreater,
            @RequestParam("longitudeLess") double longitudeLess
    ) {
        return ResponseEntity.ok(
                buildingService.getByLatitudeAndLongitude(latitudeGreater, latitudeLess, longitudeGreater, longitudeLess));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponseDto> getBuilding(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(buildingService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> uploadBuilding(
            @Valid @RequestBody BuildingRequestDto buildingRequestDto
    ) {
        Long id = buildingService.create(buildingRequestDto);
        return ResponseEntity.created(URI.create("/api/buildings/" + id)).build();
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> batchInsertBuilding(
            @Valid @RequestBody List<BuildingRequestDto> buildingRequestDtoList
    ) {
        buildingService.batchInsert(buildingRequestDtoList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBuilding(
            @PathVariable Long id,
            @Valid @RequestBody BuildingRequestDto buildingRequestDto
    ) {
        buildingService.update(id, buildingRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(
            @PathVariable Long id
    ) {
        buildingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
