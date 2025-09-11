package com.acme.piyush.maxreports;

import com.acme.piyush.maxreports.entity.Employee;
import com.acme.piyush.maxreports.entity.Report;
import com.acme.piyush.maxreports.repository.EmployeeRepository;
import com.acme.piyush.maxreports.repository.ReportRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeReportApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ReportRepository reportRepository;

    @BeforeEach
    public void setUp() {
        reportRepository.deleteAll();
        employeeRepository.deleteAll();

        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setName("Alice");

        Employee e2 = new Employee();
        e2.setId(2L);
        e2.setName("Bob");

        // Save employees FIRST
        employeeRepository.saveAll(List.of(e1, e2));

        Report r1 = new Report(); r1.setId(1L); r1.setName("R1"); r1.setEmployee(e1);
        Report r2 = new Report(); r2.setId(2L); r2.setName("R2"); r2.setEmployee(e1);
        Report r3 = new Report(); r3.setId(3L); r3.setName("R3"); r3.setEmployee(e2);

        reportRepository.saveAll(List.of(r1, r2, r3));
    }

    @Test
    public void testTopEmployee() {
        String top = reportRepository.findTopEmployeeByReports(PageRequest.of(0, 1)).get(0);
        assertEquals("Alice", top);
    }
}
