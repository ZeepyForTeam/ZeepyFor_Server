package com.zeepy.server.report.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Minky on 2021-07-18
 */

// 신고 테이블
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_sequence_gen")
    @SequenceGenerator(name = "report_sequence_gen", sequenceName = "report_sequence")
    @Column(name = "report_id")
    private Long id; // PK

    @NotNull
    @Column(name = "report_target_id")
    private Long reportId; // 신고하 테이블 의 PK

    @NotNull
    @Enumerated(EnumType.STRING)
    private TargetTableType targetTableType; // 신고 매칭 테이블 종류

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportType reportType; // 신고 유형

    @Deprecated
    private Long reportUser; // 신고한 유저 ID

    @Deprecated
    private Long targetUser; // 신고 당하는 유저 ID

    @NotEmpty
    @Lob
    private String description; // 상세 설명

    @Builder
    public Report(
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
}
