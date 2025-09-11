-- 1. For our first foray into aggregates, we're going to stick to something simple. We want to know how many facilities exist. Simply product a total count.

SELECT
   COUNT(*) AS count
FROM 
    facilities;

-- 2. Produce a count of the number of facilities that have a cost to guests of 10 or more.

SELECT 
    COUNT(*) AS count
FROM
   facilities
WHERE
   guestcost >= 10;


-- 3. Produce a count of the number of recommendations each member has made. Order by member ID.

SELECT
    recommendedby,
    COUNT(*) as count
FROM
   members
WHERE 
   recommendedby IS NOT NULL
GROUP BY
   recommendedby
ORDER BY
    recommendedby ASC;
   
-- 4. Produce a list of the total number of slots booked per facility. 
-- For now, just produce an output table consisting of facility id and slots, sorted by facility id.

SELECT
     facid,
     SUM(slots) AS Total_Slots
FROM 
     bookings
GROUP BY
     facid
ORDER BY
     facid ASC;

-- 5. Produce a list of the total number of slots booked per facility in the month of September 2012.
-- Produce an output table consisting of facility id and slots, sorted by the number of slots.

SELECT
    facid,
	SUM(slots) as total_slots
FROM
     bookings
WHERE
     starttime >= '2012-09-01 00:00:00' AND starttime < '2012-10-01 00:00:00'	 
GROUP BY
     facid
ORDER BY
     total_slots ASC;

-- select * from bookings;

-- 6. Produce a list of the total number of slots booked per facility per month in the year of 2012.
-- Produce an output table consisting of facility id and slots, sorted by the id and month.

SELECT
    facid,
	EXTRACT (MONTH from starttime),
	SUM(slots) as total_slots
FROM
     bookings
WHERE
     starttime >= '2012-01-01 00:00:00' AND starttime < '2013-01-01 00:00:00'	 
GROUP BY
    facid, 
	EXTRACT (MONTH from starttime)
ORDER BY
     facid, 
	 EXTRACT (MONTH from starttime) ASC;

-- 7. Find the total number of members (including guests) who have made at least one booking
-- query 1
SELECT 
    COUNT( memid) AS count
FROM bookings;

-- SELECT memid
-- FROM bookings
-- ORDER BY memid;

-- query 2
SELECT
    COUNT(*)
FROM ( SELECT
            memid
       FROM 
            bookings
       GROUP BY
            memid
	 ) AS count;

-- 8. Produce a list of facilities with more than 1000 slots booked.
-- Produce an output table consisting of facility id and slots, sorted by facility id. 

SELECT
    facid,
	SUM(slots) AS total_slots
FROM
    bookings	
GROUP BY
    facid 
HAVING
   SUM(slots) > 1000
ORDER BY
    facid ASC, total_slots ASC;

-- 9. Produce a list of facilities along with their total revenue. The output table should consist of facility name and revenue,
-- sorted by revenue. Remember that there's a different cost for guests and members!

SELECT
       name,
       SUM(
            CASE 
			   WHEN b.memid = 0
			       THEN b.slots * f.guestcost
			   ELSE b.slots * f.membercost
			 END  
	   ) AS revenue
FROM
       facilities f
JOIN
       bookings b
ON 
       f.facid = b.facid	   
GROUP BY
       f.name 
ORDER BY 
        revenue;

-- 10. Produce a list of facilities with a total revenue less that 1000.
-- Produce an output table consisting of facility name and
-- revenue, sorted by revenue. Remember that there's a different cost for guests and members!


SELECT
       name,
       SUM(
            CASE 
			   WHEN b.memid = 0
			       THEN b.slots * f.guestcost
			   ELSE b.slots * f.membercost
			 END  
	   ) AS revenue
FROM
       facilities f
JOIN
       bookings b
ON 
       f.facid = b.facid
	   
GROUP BY
       f.name 
HAVING 
        SUM(
            CASE 
			   WHEN b.memid = 0
			       THEN b.slots * f.guestcost
			   ELSE b.slots * f.membercost
			 END  
	   ) < 1000  
ORDER BY 
        revenue ASC
;



-- 11. Output the facility id that has the highest number of slots booked.
-- For bonus points, try a version without a LIMIT clause.
-- The version will probably look messy!

SELECT 
   facid,
   SUM(slots) as total_slots
FROM
    bookings
GROUP BY
    facid
HAVING
    SUM(slots) = (
		SELECT MAX(total_slots)
		FROM (
             SELECT SUM(slots) as total_slots
			 FROM bookings
			 GROUP BY facid
		)
	);


-- 12. Produce a list of the total number of slots booked per facility per month in the year 2012.
-- In this version, include output rows containing totals for all months per facility, and a total for all months for all facilities.
-- The output table should consist of facility id, month and slots, sorted by the id and month.
-- When calculating the aggregate values for all months and all facids, return null values in the month and facid columns.


SELECT
   facid,
   EXTRACT(MONTH from starttime) AS month,
   SUM(slots) slots
FROM
    bookings
WHERE 
    EXTRACT(YEAR FROM starttime) = 2012
GROUP BY
    ROLLUP( facid,
	        EXTRACT(MONTH FROM starttime))
ORDER BY
    facid,
	month;

-- 13. List the total hours booked per named facility
-- 13. Produce a list of the total number of hours booked per facility, remembering that slots lasts hald an hour. The output
-- table should consist of the facility id, name, hours booked, sorted by facility id. Try formatting the hours to two
-- decimal places.


-- SELECT DISTINCT
--    f.facid,
--    f.name,
--    SUM(ROUND(EXTRACT(HOUR FROM b.starttime) + 
--        EXTRACT(MINUTE FROM b.starttime) / 60.0 +
-- 	   EXTRACT (SECOND FROM b.starttime) / 3600.0, 2) ) AS total_hours
-- FROM
--    bookings b
-- JOIN
--    facilities f
--    ON b.facid = f.facid
-- GROUP BY
--    f.facid,
--    to
-- ORDER BY
--    f.facid;

SELECT DISTINCT
   f.facid,
   f.name,
   ROUND(SUM(b.slots) * 0.5, 2) AS total_hours
FROM
   bookings b
JOIN
   facilities f
   ON b.facid = f.facid
GROUP BY
   f.facid,
   f.name
ORDER BY
   f.facid;
   

-- List each member's first booking after September 1st 2012

-- 14. Poduce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.


SELECT 
    m.memid,
    m.firstname,
    m.surname,
    MIN(b.starttime) AS first_booking
FROM members m
JOIN bookings b 
    ON m.memid = b.memid
WHERE b.starttime > '2012-09-01'
GROUP BY m.memid, m.firstname, m.surname
ORDER BY m.memid;


-- 15. Produce  a list of member names, with each row containing the total member count. Order by join date, and include guest members.

SELECT
   ( SELECT COUNT(*) FROM members ) as count,
    m.firstname,
	m.surname
FROM
    members m
ORDER BY
    m.joindate ASC;
	


-- 16. Produce a monotonically increasing numbered list of members (including guests), ordered by their data of joining.
-- Remember that member IDs are not guaranteed to be sequential.

SELECT
   (memid + 1 ) AS row_number, 	
   firstname,
   surname
FROM
    members
ORDER BY
    joindate;


-- 17. Output the facility id that has highest number of slots booked. Ensure that in the event of tie, all tieing result get output.

SELECT
    facid,
	SUM(slots) as total
FROM
    bookings
GROUP BY
    facid
HAVING SUM(slots) = (
    SELECT MAX(total ) 
	FROM ( 
          SELECT 
	          SUM(slots) as total
	      FROM 
	          bookings
	      GROUP BY
	          facid
   ) AS sub
);


-- Rank members by (rounded) hours used
-- 18. Produce a list of members (including guests), along with the number of hours they've booked in facilities, roundes to the nearest ten hours. Rank them by this rounded figure, producing output of first name, surname, rounded hours, rank. Sort by rank surname, and first name.

SELECT
   m.firstname,
   m.surname,
   ROUND(SUM(b.slots) * 0.5, -1) AS hours,
   RANK() OVER (ORDER BY ROUND(SUM(b.slots) * 0.5, -1) DESC) AS rank
FROM
   members AS m
JOIN 
   bookings AS b
   ON m.memid = b.memid
GROUP BY
   m.memid,
   m.firstname,
   m.surname
ORDER BY
   rank, m.surname, m.firstname
   

-- 19. Produce a list of the top three revenue genearting facilities (including ties). Output facility name  and rnak, sorted by rank and facility name.




-- 20. Classify facilities into equally sized groups of high, average, and low based on their revenue. Order by classification and facility name.

-- 21. Based on the 3 complete months of data so far, calculate the amount of time each facility will take to repay its cost of
-- ownership. Remember to take into account ongoing monthly maintenance. Output facility name and payback time in 
-- months, order by facility name. Don't worry about differences in month lengths, we're only looking for a rough value here!

-- 22. For each day in August 2012, calculate a rolling average of total revenue over the previous 15 days. Output should contain date and revenue columns, sorted by the date. 
-- Remeber to account for the possibility of a day having zero revenue.
-- This one's a bit tough, so don't be afraid to check out the hint!


























	