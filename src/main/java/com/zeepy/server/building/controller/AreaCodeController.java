package com.zeepy.server.building.controller;

import com.zeepy.server.building.dto.AreaCodeRequestDto;
import com.zeepy.server.building.service.AreaCodeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by Minky on 2021-05-15
 */

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AreaCodeController {
    private final AreaCodeService areaCodeService;

    @PostMapping
    public ResponseEntity<Void> uploadAreaCode(
            @Valid @RequestBody AreaCodeRequestDto areaCodeRequestDto
    ) {
        Long id = areaCodeService.create(areaCodeRequestDto);
        return ResponseEntity.created(URI.create("/api/codes/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAreaCode(
            @PathVariable Long id,
            @Valid @RequestBody AreaCodeRequestDto areaCodeRequestDto
    ) {
        areaCodeService.update(id, areaCodeRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAreaCode(
            @PathVariable Long id
    ) {
        areaCodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
