package com.zeepy.server.building.controller;

import com.zeepy.server.building.dto.AreaCodeRequestDto;
import com.zeepy.server.building.service.AreaCodeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Created by Minky on 2021-05-15
 */

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AreaCodeController {
    private final AreaCodeService areaCodeService;

    @PostMapping
    public ResponseEntity<Void> uploadAreaCode(
            @RequestBody AreaCodeRequestDto areaCodeRequestDto
    ) {
        Long id = areaCodeService.create(areaCodeRequestDto);
        return ResponseEntity.created(URI.create("api/code/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAreaCode(
            @PathVariable Long id,
            @RequestBody AreaCodeRequestDto areaCodeRequestDto
    ) {
        areaCodeService.update(id, areaCodeRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAreaCode(
            @PathVariable Long id
    ) {
        areaCodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}