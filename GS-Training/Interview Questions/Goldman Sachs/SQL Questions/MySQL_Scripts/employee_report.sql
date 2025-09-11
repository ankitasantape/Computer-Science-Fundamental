-- Employee Reports
-- You have two entities:
-- Employee(id, name)
-- Report(id, name, empID)
-- Find the employee name who has written the maximum number of reports.

CREATE DATABASE GS_SQL_INTERVIEW;
commit;

USE GS_SQL_INTERVIEW;

-- Drop tables if they exist
DROP TABLE IF EXISTS Report;
DROP TABLE IF EXISTS Employee;

-- 1. Create Employee Table
CREATE TABLE Employee (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 2. Create Report Table
CREATE TABLE Report (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    empID INT,
    FOREIGN KEY (empID) REFERENCES Employee(id)
);

-- 3. Insert Sample Employees
INSERT INTO Employee (id, name) VALUES
(1, 'Alice'),
(2, 'Bob'),
(3, 'Carol');

-- 4. Insert Sample Reports
INSERT INTO Report (id, name, empID) VALUES
(101, 'Report A', 1),
(102, 'Report B', 1),
(103, 'Report C', 2),
(104, 'Report D', 1),
(105, 'Report E', 3),
(106, 'Report F', 2);

-- 5. Query: Find the employee with the most reports
SELECT e.name
FROM Employee e
JOIN (
    SELECT empID, COUNT(*) AS report_count
    FROM Report
    GROUP BY empID
    ORDER BY report_count DESC
    LIMIT 1
) r ON e.id = r.empID;
