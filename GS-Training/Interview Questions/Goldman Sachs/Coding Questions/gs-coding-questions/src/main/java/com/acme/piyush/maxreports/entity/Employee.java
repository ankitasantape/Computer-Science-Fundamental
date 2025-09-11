/*Employee Reports
You have two entities:
Employee(id, name)
Report(id, name, empID)
Find the employee name who has written the maximum number of reports.*/

package com.acme.piyush.maxreports.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Employee {
    @Id
    private Long id;

    private String name;

    // One-to-Many relationship
    @OneToMany(mappedBy = "employee")
    private List<Report> reports;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
