package com.zeepy.server.report.controller;

import com.zeepy.server.report.dto.ReportRequestDto;
import com.zeepy.server.report.dto.ReportResponseDto;
import com.zeepy.server.report.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by Minky on 2021-07-19
 */

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getReports() {
        return ResponseEntity.ok().body(reportService.getReportList());
    }

    @PostMapping
    public ResponseEntity<Void> saveReport(
            @Valid @RequestBody ReportRequestDto reportRequestDto
    ) {
        Long id = reportService.create(reportRequestDto);
        return ResponseEntity.created(URI.create("api/reports/" + id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long id
    ) {
        reportService.deleteReportById(id);
        return ResponseEntity.noContent().build();
    }
}
