-- 1. How can you produce a list of the start times for bookings by members named 'David Farrell'?


SELECT
  b.starttime
FROM
   members as m
JOIN
   bookings as b
ON
   m.memid = b.memid
WHERE
   m.firstname = 'David' 
AND m.surname = 'Farrell';

-- 2. How can you produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'?
-- Return a list of start time and facility name pairings, ordered by the time.

SELECT
  b.starttime,
  f.name
FROM
   facilities as f
JOIN
   bookings as b
ON
   f.facid = b.facid
WHERE
   f.name LIKE 'Tennis Court%' 
AND 
   DATE(b.starttime) = '2012-09-21' 
ORDER BY
   starttime ASC; 


-- 3. How can you output a list of all members who have recommended another member? Ensure that there are no duplicates
-- in the list, and that results are ordered by (surname, firstname).

SELECT DISTINCT
    firstname,
	surname
FROM
    members
WHERE
    memid IN (
		SELECT recommendedby
		FROM
		     members
		WHERE
		     recommendedby IS NOT NULL
	)
ORDER BY
   surname, firstname;
   

-- query 2

SELECT DISTINCT
    m1.firstname,
    m1.surname
FROM
    members m1
JOIN
    members m2
ON
    m2.recommendedby = m1.memid
ORDER BY
    m1.surname,
    m1.firstname;


-- 4. How can you output a list of all members, including the individual who recommended them (if any)? Ensure that results 
-- are ordered by (surname, firstname).


SELECT DISTINCT
    m1.firstname as memfname,
    m1.surname as memsname,
	m2.firstname as recfname,
	m2.surname as recsname
FROM
    members m1
LEFT JOIN
    members m2
ON
    m1.recommendedby = m2.memid
GROUP BY
    m1.memid, m1.firstname, m1.surname, m2.firstname, m2.surname
ORDER BY
    m1.surname,
    m1.firstname;


-- 5. How can you produce a list of all members who have used a tennis court? Include in your output the name of the court,
-- and the name of the member formatted as a single column. Ensure no duplicate data, and order by the member name followed by the facility name.

SELECT
   CONCAT(m.firstname, ' ', m.surname) AS member,
   f.name AS facility
FROM
   members m
JOIN
   bookings b
   ON m.memid = b.memid
JOIN
   facilities f
   ON f.facid = b.facid
WHERE 
   f.name LIKE 'Tennis Court%' 
ORDER BY 
   member ASC,
   facility ASC;
   

-- 6. How can you produce a list of bookings on the day of 2012-09-14. Which will cost the member (or guest) more than $30?
-- Remember that guests have different costs to members (the listed costs are per half-hour 'slot'), and the guest user is
-- always ID 0. Include in your output the name of the facility, the name of the member formatted as a single column, and
-- the cost. Order by descending cost, and do not use any subqueries.


SELECT
      (m.firstname||' '||m.surname) AS member,
	  f.name,
	  CASE 
	      WHEN b.memid = 0
		      THEN 
		          b.slots * f.guestcost 
		      ELSE
		          b.slots * f.membercost 
		      END AS cost  
FROM
   bookings b
JOIN 
   facilities f
   ON b.facid = f.facid
JOIN
   members m
   ON m.memid = b.memid
WHERE
   DATE(b.starttime) = '2012-09-14'
   AND
   ( CASE 
	      WHEN b.memid = 0
		      THEN 
		          b.slots * f.guestcost 
		      ELSE
		          b.slots * f.membercost 
		      END) > 30
ORDER BY
   cost DESC;
			  
-- 7. How can you output a list of all members, including the individual who recommended them (if any), without using any
-- joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column
-- and ordered.

SELECT
   m1.firstname || ' ' || m1.surname AS member,
   m2.firstname || ' ' || m2.surname AS recommender
FROM
   members m1
LEFT JOIN
   members m2
   ON m1.recommendedby = m2.memid
ORDER BY member;   


-- 8. The Produce a list of costly bookings exercise contained some messy logic: we had to calculate the booking cost in both
-- the WHERE clause and the case statement. Try to simplyfy this calculation using subqueries. For reference, the quetion was:
-- How can you produce a list of bookings on the day of 2012-09-14 which will cost the member (or guest) more that $30?
-- Remember that guests have different costs to members (the listed costs are per half-hour 'slot'), and the guest user is
-- always ID 0. Include in your output the name of the facility, the name of the member formatted as a single column, and 
-- the cost. Order by descending cost.


SELECT
    (m.firstname || ' ' || m.surname ) AS member,
	f.name,
	(CASE 
	     WHEN b.memid = 0
		     THEN 
			      f.guestcost * b.slots
			 ELSE 
			      f.membercost * b.slots
		     END ) AS cost
FROM 
    bookings b
JOIN
    facilities f
    ON b.facid = f.facid
JOIN
    members m
	ON b.memid = m.memid
WHERE
    DATE(b.starttime) = '2012-09-14'
	AND 
	(CASE 
	     WHEN b.memid = 0
		     THEN 
			      f.guestcost * b.slots
			 ELSE 
			      f.membercost * b.slots
		     END ) > 30
ORDER BY 
    cost DESC;











