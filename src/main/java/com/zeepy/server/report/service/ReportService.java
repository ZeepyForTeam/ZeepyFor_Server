package com.zeepy.server.report.service;

import com.zeepy.server.report.repository.ReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by Minky on 2021-07-18
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ReportService {
    private final ReportRepository reportRepository;
}
