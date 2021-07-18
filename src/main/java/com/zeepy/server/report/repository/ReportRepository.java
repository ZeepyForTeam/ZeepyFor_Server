package com.zeepy.server.report.repository;

import com.zeepy.server.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Minky on 2021-07-18
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
}
