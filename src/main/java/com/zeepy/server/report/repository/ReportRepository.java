package com.zeepy.server.report.repository;

import com.zeepy.server.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Minky on 2021-07-18
 */

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
