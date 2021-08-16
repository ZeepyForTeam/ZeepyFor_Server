package com.zeepy.server.report.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.report.dto.ReportRequestDto;
import com.zeepy.server.report.dto.ReportResponseDto;
import com.zeepy.server.report.service.ReportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

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
