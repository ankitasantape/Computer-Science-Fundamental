select * from employee;

select all salary from employee;
select distinct salary from employee;

select *
from department;

select *
from project;

select *
from dept_locations;

select *
from department , dept_locations ;

select * 
from  department , dept_locations
where department.dnumber = dept_locations.dnumber ;

select * from employee e, employee s;

select e.fname, s.lname from employee e, employee s; 

select e.fname as fname_eT1, s.lname as lname_eT2 from employee e , employee s;

--Query1: Retrieve the birth_date and address of all the employees whose name is 'John B Smith'. 
--Solution:
select bdate, address
from employee 
where fname = 'John' and minit = 'B' and lname = 'Smith';

--Query2: Retrieve the name and address of all the employees who work for the 'Research' department. 
--Solution: 
select e.fname, e.address, d.dname, d.dnumber  
from employee e, department d 
where  e.dno = d.dnumber and d.dname = 'Research'; 

--Query3: For every project located in 'Stafford', list the project number, the controlling department number, and the department manager's last name, address and birthdate.
--Solution:  
select p.pnumber , d.dnumber , e.lname , e.address , e.bdate  
from employee e, project p, department d 
where  p.dnum  = d.dnumber and d.mgr_ssn = e.ssn  and p.plocation = 'Stafford'; 

--Query4: For every employee retrieve the employee's first and lastname and the first & last name of his or her immediate supervisor. 
--Solution: e-employee , s-supervisor/manager/boss
select e.fname , e.lname , s.fname , s.lname 
from employee e , employee s
where e.super_ssn = s.ssn ;

-- print only supervisors's name
select distinct super_ssn from employee; 
select distinct s.ssn, s.fname , s.lname 
from employee e , employee s
where e.super_ssn = s.ssn ;

--Query5: Retrieve the salary of every employee and all the distinct salary value. 
--Solution:
select distinct e.salary 
from employee e; 

--query6: Make a list of all project numbers for projects that involve an employee whose last name is 'Smith', either as a worker or as a manager of the department that controls the project. 
--Solution: 
select distinct p.pnumber 
from employee e, works_on wo,  project p   
where e.ssn = wo.essn and p.pnumber = wo.pno and e.lname = 'Smith'
union
select distinct p.pnumber  
from employee e , department d  , project p 
where p.dnum = d.dnumber and d.mgr_ssn = e.ssn and e.lname = 'Smith';

--query7: Retrieve all employees whose address is in 'Houston TX'. 
--Solution: 
select e.fname , e.lname 
from employee e 
where address like '%Houston TX%';

--query8: Find the employees who were born during 1950's
--Solution --> most preffered use LIKE
select fname, lname 
from employee 
where to_char(bdate, 'YYYY-MM-DD') like '194_______';

--or you can solve this query using extract()

select fname, lname 
from employee
where extract(year from bdate) >= 1970 and extract(year from bdate) <= 1980; 

--query9: Show resulting salaries if every employee working on the 'ProductX' project is given a raise of 50% 
--Solution 
select e.salary  
from employee e , project p , works_on wo 
where e.ssn = wo.essn and wo.pno = p.pnumber and p.pname = 'ProductX';

--query10: Retrieve all employees in department 5 whose salary is between $40,000 and $50,000 
--Solution 

select fname, lname 
from employee 
where dno=5 and salary >= 40000  and salary <= 50000 ; 

--using BETWEEN(inclusive)
select fname, lname 
from employee 
where dno=5 and salary between 40000 and 50000 ; 

-- use of order by clause
-- by default ascending print hoge
select * from employee order by fname;

select * from employee order by fname desc;

--Query11(i): Retrieve a list of employees and the projects they are working on, ordered by department, and within each department, ordered alphabetically by last name, then first name. 

select 
     e.fname ,
     e.lname ,
     p.pname 
from employee e , 
     project p , 
     works_on wo 
where 
     e.ssn = wo.essn  
     and p.pnumber = wo.pno  
order by 
     e.dno , 
     e.lname, 
     e.fname;

--Query11(ii) : Same as above first sorted in descending alphabetical order of dname and ascending alphabetical order of last name and then first name. 
    
--Query12: Retrieve names of all employees who do not have supervisors.

select * 
from employee 
where super_ssn is null;

--Query13: Retrieve the first & last name of all the employees who are someone's supervisor ? 

--wrong
select fname, lname, ssn, super_ssn  
from employee
where super_ssn in (select ssn from employee);

--correct
select fname, lname, ssn, super_ssn  
from employee
where employee.ssn in (select super_ssn from employee);


--random query just for example. employee_dt table sirf ram me create hogi physically koi existence nhi hoga

select fname, lname 
from (select fname, lname from employee) as employee_dt;

--Query14: Retrieve the salary of all the employees whose salary is greater than the salary of all the employees in department 5.
--Solution: 
select e2.salary 
from employee e2
where e2.salary > (select e.salary from employee e where e.dno = 5);


select e2.fname , e2.lname , e2.salary 
from employee e2
where e2.salary > all(select e.salary from employee e where e.dno = 5);


select e2.fname , e2.lname , e2.salary 
from employee e2
where e2.salary > (select max(e.salary) from employee e where e.dno = 5);

select e2.fname , e2.lname , e2.salary 
from employee e2
where e2.salary > any(select e.salary from employee e where e.dno = 5);

--Query15: Retrieve the name of each employee who has a dependent with the same first name and is the same sex as employee. 
--Solution:
select e.fname, e.lname 
from employee e
where e.fname in (
           select dependent_name  
           from dependent
           where dependent.sex = e.sex
           and dependent.essn = e.ssn  );

--Does Exists
--Query16: Retrieve the names of all employees who have no dependent 
--Solution:

select fname, lname 
from employee e 
where not exists (select * from dependent d where e.ssn = d.essn);
          
--Query17: List the names of all managers who have at least one dependent. 
--Solution:
select fname, lname 
from employee
where ssn in( 
    select super_ssn 
    from employee
) 
and exists 
(   select * 
    from dependent 
    where employee.ssn = dependent.essn );
     
--Query18: Retrieve the SSNs of all employees who work on project numbers 1, 2, or 3.  
   
  
--Joins   
create table course(course_id numeric primary key,
course_name varchar(20));

create table student(sid numeric primary key,
sname varchar(20), 
course_id numeric references course(course_id));

drop table if exists student cascade; 
drop table if exists course cascade; 

create table course(course_id numeric,
course_name varchar(20));


create table student(sid numeric,
sname varchar(20), 
course_id numeric);


insert into course values(101, 'DBMS');
insert into course values(100, 'OS');
insert into course values(103, 'CN');
insert into course values(104, 'SD');
--try to run null wali query after removing primary key constraint
insert into course values(null, 'JD');
insert into course values(null, 'TOC');

insert into student values(1, 'A', 101);
insert into student values(2, 'B', 100);
insert into student values(3, 'A', 103);
insert into student values(5, 'D', 100);
insert into student values(6, 'E', 101);
insert into student values(7, 'F', 103);
--try to run null wali query after removing primary key constraint
insert into student values(8, 'F', null);
insert into student values(null, 'F', null);
insert into student values(null, null, null);
insert into student values(null, null, null);



-- if you want to remove tuple contains with null
delete from student where course_id is null;
delete from course where course_id is null;

select * from course;
select * from student;
--it will show same o/p before and after adding null and removing null becoz null = null -> unknown = unknown -> unknown
select * from course inner join student on course.course_id = student.course_id;  
 
select * from course join student on course.course_id = student.course_id;  
 
select * from course right join student on course.course_id = student.course_id;  
 
select * from course left outer join student on course.course_id = student.course_id;  
 
select * from course natural join student;

alter table student rename course_id to s_course_id;

select * from information_schema.tables;

--Aggregate 
--Query1: Find the sum of the salaries of all the employees, the maximum salary, the minimum salary & the average salary
--Solution
select sum(salary), min(salary), max(salary), avg(salary) 
from employee; 

--Query2(i): Retrieve the total number of employees in the company and
--Solution:
select count(*) 
from employee;

--Query2(ii): Retrieve the total number of employees in the 'Research' department 
--Solution:
select count(*) 
from employee e, department d  
where e.dno = d.dnumber and d.dname = 'Research'; 

--Query3: Count the number of distinct salaries in the table.
--Solution
select distinct count(salary)
from employee; 

select count(distinct salary)
from employee; 


-- Group by 
--error
select * 
from employee
group by dno;

select dno, count(salary)
from employee
group by dno;

--Query1 (i)For each department, retrieve the department number, the number of employees in the department & their average salary. 
--Solution
select dno, count(*), avg(salary)
from employee
group by dno;

--error-> select clause me sirf aggregate function and group by me jo attribute diye gaye hai wahi use kr skte hai
select dno, count(*), avg(salary), fname
from employee
group by dno;

--
select dno, count(*), avg(salary)
from employee
group by dno, ssn;

select dno, ssn, count(*), avg(salary)
from employee
group by dno, ssn;

--Query5 (i) Retrieve the ssn of all the Supervisors & the number of their immediate supervisor.
--Solution:
select super_ssn, count(ssn)  
from employee
group by super_ssn; 

--if you have to remove supervisor with null value
select super_ssn, count(ssn)  
from employee
where super_ssn is not null
group by super_ssn;  

--use of having clause -> having only works on group by clause 
select super_ssn , count(ssn)
from employee
group by super_ssn
having count(ssn) > 2;
--or you can write it like this 
select super_ssn , count(ssn)
from employee
group by super_ssn
having count(*) > 2;

--Query5 (ii) Retrieve the SSN & names of all the managers/supervisors & the number of their immediate supervisors
--Solution:
select e.fname, e.lname  
from employee e cross join (
     select super_ssn, count(ssn)  
     from employee
     where super_ssn is not null
     group by super_ssn ) as t1
where 
     t1.super_ssn = e.ssn ;

-- if you want the count that supervisee
select e.fname, e.lname, t1.count_of_supervisee  
from employee e cross join (
     select super_ssn, count(ssn) as count_of_supervisee 
     from employee
     where super_ssn is not null
     group by super_ssn ) as t1
where 
     t1.super_ssn = e.ssn ;
   
--if you want ssn with count 
select e.ssn , e.fname, e.lname, t1.count_of_supervisee  
from employee e cross join (
     select super_ssn, count(ssn) as count_of_supervisee 
     from employee
     where super_ssn is not null
     group by super_ssn ) as t1
where 
     t1.super_ssn = e.ssn ;    

--Query6: For each project, retrieve the project number, the project name and the number of employees from department 5 who work on the project. 









