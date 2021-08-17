package com.zeepy.server.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeepy.server.report.domain.Report;

/**
 * Created by Minky on 2021-07-18
 */

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
