package com.zeepy.server.report.service;

import com.zeepy.server.report.domain.Report;
import com.zeepy.server.report.dto.ReportRequestDto;
import com.zeepy.server.report.dto.ReportResponseDto;
import com.zeepy.server.report.repository.ReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Minky on 2021-07-18
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public List<ReportResponseDto> getReportList() {
        List<Report> reportList = reportRepository.findAll();
        return ReportResponseDto.listOf(reportList);
    }

    @Transactional
    public Long create(ReportRequestDto reportRequestDto) {
        // TODO:(테이블 검증 로직 추가)
        Report report = reportRequestDto.returnReportEntity();
        Report save = reportRepository.save(report);
        return save.getId();
    }

    @Transactional
    public void deleteReportById(Long id) {
        reportRepository.deleteById(id);
    }
}
