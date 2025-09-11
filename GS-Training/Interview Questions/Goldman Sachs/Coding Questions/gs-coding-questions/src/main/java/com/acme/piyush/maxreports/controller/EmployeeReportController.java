package com.acme.piyush.maxreports.controller;


import com.acme.piyush.maxreports.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeReportController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/top-reporter")
    public String getTopReporter() {
        return reportRepository.findTopEmployeeByReports(PageRequest.of(0, 1)).get(0);
    }
}
