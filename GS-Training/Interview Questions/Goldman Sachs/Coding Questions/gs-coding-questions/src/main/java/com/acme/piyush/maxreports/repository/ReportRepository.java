package com.acme.piyush.maxreports.repository;

import com.acme.piyush.maxreports.entity.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("""
        SELECT e.name
        FROM Report r
        JOIN r.employee e
        GROUP BY e.id, e.name
        ORDER BY COUNT(r) DESC
    """)
    List<String> findTopEmployeeByReports(Pageable pageable);
}
