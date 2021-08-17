package com.zeepy.server.report.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.annotation.Enum;
import com.zeepy.server.report.domain.Report;
import com.zeepy.server.report.domain.ReportType;
import com.zeepy.server.report.domain.TargetTableType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-07-19
 */

@NoArgsConstructor
@Setter
@Getter
public class ReportRequestDto {
	@NotNull(message = "reportId cannot be Null")
	private Long reportId;

	@Enum(enumClass = TargetTableType.class, ignoreCase = true, message = "TargetTableType is not valid")
	private String targetTableType;

	@Enum(enumClass = ReportType.class, ignoreCase = true, message = "ReportType is not valid")
	private String reportType;

	@NotNull(message = "reportUser cannot be Null")
	private Long reportUser;

	@NotNull(message = "targetUser cannot be Null")
	private Long targetUser;

	@NotBlank(message = "Description cannot be Empty")
	private String description;

	@Builder
	public ReportRequestDto(
		Long reportId,
		String targetTableType,
		String reportType,
		Long reportUser,
		Long targetUser,
		String description
	) {
		this.reportId = reportId;
		this.targetTableType = targetTableType;
		this.reportType = reportType;
		this.reportUser = reportUser;
		this.targetUser = targetUser;
		this.description = description;
	}

	public Report returnReportEntity() {
		return new Report(
			null,
			this.reportId,
			TargetTableType.valueOf(this.targetTableType),
			ReportType.valueOf(this.reportType),
			this.reportUser,
			this.targetUser,
			this.description
		);
	}
}
