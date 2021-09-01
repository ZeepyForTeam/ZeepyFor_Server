package com.zeepy.server.report.service;

import java.util.List;

import com.zeepy.server.common.job.AsyncJob;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.email.domain.AdminEmail;
import com.zeepy.server.email.repository.EmailRepository;
import com.zeepy.server.email.util.EmailSendUtility;
import com.zeepy.server.report.domain.Report;
import com.zeepy.server.report.dto.ReportRequestDto;
import com.zeepy.server.report.dto.ReportResponseDto;
import com.zeepy.server.report.repository.ReportRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-07-18
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReportService {
    private final ReportRepository reportRepository;
    private final EmailRepository emailRepository;
    private final EmailSendUtility emailSendUtility;
    private final AsyncJob asyncJob;

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

        /**
         * Email 전송 로직 추가
         */

        asyncJob.onStart(() -> {
            List<AdminEmail> adminEmails = emailRepository.findAll();
            for (AdminEmail adminEmail : adminEmails) {
                emailSendUtility.mailSend(
                        adminEmail.getEmail(),
                        "유저 신고가 들어왔습니다.",
                        save.getDescription());
            }
        });

        return save.getId();
    }

    @Transactional
    public void deleteReportById(Long id) {
        reportRepository.deleteById(id);
    }
}
