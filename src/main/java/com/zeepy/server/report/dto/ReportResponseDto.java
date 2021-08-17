package com.zeepy.server.report.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zeepy.server.report.domain.Report;
import com.zeepy.server.report.domain.ReportType;
import com.zeepy.server.report.domain.TargetTableType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-07-19
 */

@NoArgsConstructor
@Setter
@Getter
public class ReportResponseDto {
	private Long id;
	private Long reportId;
	private TargetTableType targetTableType;
	private ReportType reportType;
	private Long reportUser;
	private Long targetUser;
	private String description;

	public ReportResponseDto(
		Long id,
		Long reportId,
		TargetTableType targetTableType,
		ReportType reportType,
		Long reportUser,
		Long targetUser,
		String description
	) {
		this.id = id;
		this.reportId = reportId;
		this.targetTableType = targetTableType;
		this.reportType = reportType;
		this.reportUser = reportUser;
		this.targetUser = targetUser;
		this.description = description;
	}

	public static ReportResponseDto of(Report report) {
		return new ReportResponseDto(
			report.getId(),
			report.getReportId(),
			report.getTargetTableType(),
			report.getReportType(),
			report.getReportUser(),
			report.getTargetUser(),
			report.getDescription()
		);
	}

	public static List<ReportResponseDto> listOf(List<Report> reportList) {
		return reportList
			.stream()
			.map(ReportResponseDto::of)
			.collect(Collectors.toList());
	}
}
