-- Problem
-- You have two tables:

-- Employee: contains id, name, division_id

-- Division: contains id, division_name

-- Goal: Get a count of employees per division (including divisions with 0 employees).

USE gs_sql_interview;

-- Division table
CREATE TABLE Division (
    id INT PRIMARY KEY,
    division_name VARCHAR(100)
);

-- Employee table
CREATE TABLE Employee1 (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    division_id INT,
    FOREIGN KEY (division_id) REFERENCES Division(id)
);

INSERT INTO Division (id, division_name) VALUES
(1, 'Engineering'),
(2, 'Marketing'),
(3, 'HR');

INSERT INTO Employee1 (id, name, division_id) VALUES
(101, 'Alice', 1),
(102, 'Bob', 1),
(103, 'Carol', 2);

-- SQL Query – Count of Employees by Division--  

SELECT 
    d.division_name,
    COUNT(e.id) AS employee_count
FROM 
    Division d
LEFT JOIN 
    Employee1 e ON d.id = e.division_id
GROUP BY 
    d.division_name;

-- LEFT JOIN ensures we include all divisions, even those with no employees.

-- COUNT(e.id) counts employees in each division (ignores NULLs).