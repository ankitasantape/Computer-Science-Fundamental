-- To Practice SQL :- https://www.sql-practice.com

-- EASY-1: Show first name, last name, and gender of patients who's gender is 'M'
SELECT first_name, last_name, gender FROM patients
WHERE gender IS 'M'

-- EASY-2: Show first name and last name of patients who does not have allergies (null).
SELECT first_name, last_name FROM patients 
WHERE allergies IS NULL;

-- EASY-3: Show first name of patients that start with the letter 'C'
SELECT first_name FROM patients WHERE first_name LIKE 'C%';

-- EASY-4: Show first name and last name of patients that weight within the range of 100 to 120 (inclusive)
SELECT first_name, last_name FROM patients WHERE weight BETWEEN 100 and 120;

-- EASY-5: Show first name and last name of patients that weight within the range of 100 to 120 (inclusive)
SELECT first_name, last_name FROM patients WHERE weight BETWEEN 100 and 120;

-- EASY-6: Update the patients table for the allergies column. If the patient's allergies is null then replace it with 'NKA'
UPDATE patients SET allergies = 'NKA' WHERE allergies IS NULL;

-- EASY-7: Show first name and last name concatinated into one column to show their full name. 
SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM patients;

-- EASY-8: Show first name, last name, and the full province name of each patient.
-- Example: 'Ontario' instead of 'ON'
--Ans1
select pt.first_name, pt.last_name, prv.province_name
from patients as pt, provinces as prv
where pt.province_id = prv.province_id;

--Ans2
SELECT first_name,last_name,province_name FROM patients
JOIN provinces on provinces.province_id = patients.province_id;

-- EASY-9: Show how many patients have a birth_date with 2010 as the birth year. 
--Ans1
SELECT COUNT(*) as total_patients FROM patients
WHERE YEAR(birth_date) = 2010;

--Ans2
select 
count( case when year(birth_date) is 2010 then 1 end )
from patients;

-- EASY-10: Show the first_name, last_name, and height of the patient with the greatest height.
SELECT first_name, last_name, MAX(height) as height
FROM patients;

-- EASY-11: Show all columns for patients who have one of the following patient_ids: 1,45,534,879,1000
SELECT * FROM patients 
WHERE patient_id IN (1,45,534,879,1000);

-- EASY-12: Show the total number of admissions
SELECT COUNT(*) as total_admissions
FROM admissions;

-- EASY-13: Show all the columns from admissions where the patient was admitted and discharged on the same day.
SELECT *
FROM admissions
WHERE admission_date = discharge_date;

-- EASY-14: Show the total number of admissions for patient_id 573.
SELECT patient_id,
COUNT(*) AS total_admissions
FROM admissions
WHERE patient_id = 573;

-- EASY-15: Write a query to find the first_name, last name and birth date of patients who have height more than 160 and weight more than 70
SELECT 
      first_name, 
      last_name, 
      birth_date
FROM patients
WHERE 
      height > 160 AND weight > 70;

-- EASY-16: Write a query to find list of patients first_name, last_name, and allergies from Hamilton where allergies are not nka or null
SELECT first_name,
       last_name, 
       allergies
FROM patients
WHERE 
     city IS 'Hamilton' 
     AND allergies IS NOT NULL 
     AND allergies != 'NKA';
   
 -- OR
 
SELECT 
       first_name, 
       last_name,
       allergies
FROM patients
WHERE 
      city IS 'Hamilton' 
      AND allergies IS NOT null
      AND allergies IS NOT 'NKA';

-- MEDIUM-1: Show unique birth years from patients and order them by ascending.
SELECT DISTINCT YEAR(birth_date) AS birth_year FROM patients 
ORDER BY birth_year;

-- MEDIUM-2: Show unique first names from the patients table which only occurs once in the list.
-- For example, if two or more people are named 'John' in the first_name column then don't include their name in the output list. 
-- If only 1 person is named 'Leo' then include them in the output.
SELECT first_name FROM patients GROUP BY first_name HAVING COUNT(first_name) = 1;

-- MEDIUM-3: Show patient_id and first_name from patients where their first_name start and ends with 's' and is at least 6 characters long.
-- Ans1
SELECT patient_id, first_name FROM patients
WHERE first_name LIKE 's____%s';
-- or

SELECT patient_id, first_name FROM patients
WHERE first_name LIKE 's%____s';

-- Ans2
SELECT patient_id, first_name FROM patients
WHERE length(first_name) >= 6 AND first_name LIKE 's%s';


-- MEDIUM-4: Show patient_id, first_name, last_name from patients whos primary_diagnosis is 'Dementia'. Primary diagnosis is stored in the admissions table.
-- Ans1
SELECT pt.patient_id, pt.first_name, pt.last_name FROM patients AS pt inner JOIN admissions AS adm
ON pt.patient_id IS adm.patient_id AND
   adm.primary_diagnosis = 'Dementia';

-- Ans2
SELECT patients.patient_id, first_name, last_name FROM patients
JOIN admissions ON admissions.patient_id = patients.patient_id
WHERE primary_diagnosis = 'Dementia';

-- Ans3
SELECT 
      pt.patient_id, 
      pt.first_name, 
      pt.last_name
FROM 
     patients AS pt, 
     admissions AS ad
WHERE 
     ad.primary_diagnosis IS 'Dementia'
     AND ad.patient_id = pt.patient_id;

-- MEDIUM-5: Show patient_id, first_name, last_name from the patients table. Order the rows by the first_name ascending and then by the last_name descending.
SELECT patient_id, first_name, last_name FROM patients
ORDER BY first_name ASC, last_name DESC;


-- MEDIUM-6: Show the total amount of male patients and the total amount of female patients in the patients table
-- Ans1
SELECT (SELECT count(*) FROM patients WHERE gender='M') AS male_count, 
  (SELECT count(*) FROM patients WHERE gender='F') AS female_count;  
  
-- Ans2
select count(case when gender is 'M' then 1 end) as male_count,
       count(case when gender is 'F' then 1 end) as female_count
from patients;  

-- MEDIUM-7: Show first and last name, allergies from patients which have allergies to either 'Penicillin' or 'Morphine'. Show results ordered ascending by allergies then by first_name then by last_name.
SELECT first_name, last_name, allergies FROM patients
  WHERE allergies IN ('Penicillin', 'Morphine')
  ORDER BY allergies, first_name, last_name;


-- MEDIUM-8: Show patient_id, primary_diagnosis from admissions. Find patients admitted multiple times for the same primary_diagnosis.
SELECT patient_id, primary_diagnosis FROM admissions 
  GROUP BY patient_id, primary_diagnosis   
  HAVING COUNT(*) > 1;
  
-- MEDIUM-9: Show the city and the total number of patients in the city in the order from most to least patients.
-- Ans1
SELECT city, COUNT(*) AS num_patients
  FROM patients
  GROUP BY city
  ORDER BY num_patients DESC;
  
-- Ans2
SELECT city, COUNT(patient_id) AS num_patients
FROM patients
GROUP BY city
ORDER BY count(patient_id) DESC;

-- MEDIUM-10: Show first name, last name and role of every person that is either patient or physician. The roles are either "Patient" or "Physician" 
SELECT first_name, last_name, 'Patient' as 'Role' FROM patients
UNION
SELECT first_name, last_name, 'Physician' as 'Role' FROM physicians;

-- MEDIUM-11: Show the city and the total number of patients in the city in the order from most to least patients.
SELECT city, COUNT(*) AS num_patients
  FROM patients
  GROUP BY city
  ORDER BY num_patients DESC;
  
-- MEDIUM-12: Show all allergies ordered by popularity. Remove 'NKA' and NULL values from query.
-- Ans1
SELECT allergies, COUNT(*) as total_diagnosis FROM patients
WHERE NOT allergies ='NKA' AND allergies NOT NULL
GROUP BY allergies
ORDER BY total_diagnosis DESC

-- Ans2
select allergies, count(allergies) as total_diagnosis
from patients
where allergies is not 'NKA' and allergies is not NULL
group by allergies
order by total_diagnosis desc;

-- Ans3
select allergies, count(patient_id) as total_diagnosis
from patients
group by allergies
having allergies is not null
      and allergies is not 'NKA'
order by total_diagnosis desc;

-- MEDIUM-13: Show all patient's first_name, last_name, and birth_date who were born in the 1970s decade. Sort the list starting from the earliest birth_date.
-- Ans1
SELECT first_name, last_name, birth_date FROM patients
WHERE YEAR(birth_date) between 1970 and 1979
ORDER BY birth_date ASC;

-- Ans2
SELECT FIRST_NAME, LAST_NAME, BIRTH_DATE FROM PATIENTS
WHERE YEAR(BIRTH_DATE) LIKE '197%'
ORDER BY BIRTH_DATE ASC;


-- MEDIUM-14: We want to display each patient's full name in a single column. Their last_name in all upper letters must appear first, then first_name in all lower case letters. Separate the last_name and first_name with a comma. Order the list by the first_name in decending order
-- EX: SMITH,jane
SELECT CONCAT(UPPER(last_name),',',LOWER(first_name)) as new_name_format FROM patients
ORDER BY first_name DESC;

-- MEDIUM-15: Show the cities where the patient's average weight, rounded-up, is less than 70kg. Sort the list by highest to lowest avg_weight.
SELECT city, CEIL(AVG(weight)) as avg_weight
FROM patients
GROUP BY city
HAVING avg_weight < 70
ORDER BY avg_weight desc;

-- MEDIUM-16: Show the province_id(s), sum of height; where the total sum of its patient's height is greater than or equal to 7,000.
SELECT province_id, SUM(height) as sum_height
FROM patients
GROUP BY province_id
HAVING sum_height >= 7000;

-- MEDIUM-17: Show the difference between the largest weight and smallest weight for patients with the last name 'Maroni'.
SELECT (MAX(weight) - MIN(weight)) as weight_delta
FROM patients
WHERE last_name = 'Maroni';

-- MEDIUM-18: Based on the cities that our patients live in, show unique cities that are in province_id 'NS'?
SELECT DISTINCT(city) as unique_cities
FROM patients
WHERE province_id = 'NS';

-- MEDIUM-19: Show all of the month's day numbers and how many admission_dates occurred on that number. Sort by the day with most admissions to least admissions.
SELECT DAY(admission_date) AS day_number,
COUNT(*) AS number_of_admissions
FROM admissions
GROUP BY day_number
ORDER BY number_of_admissions DESC

-- MEDIUM-20: Show the patient_id, nursing_unit_id, room, and bed for patient_id 542's most recent admission_date.
--ANS1:
SELECT
  patient_id,
  nursing_unit_id,
  room,
  bed
FROM admissions
WHERE patient_id = 542
GROUP BY patient_id
HAVING
  admission_date = MAX(admission_date);
  
--ANS2:
SELECT
  patient_id,
  nursing_unit_id,
  room,
  bed
FROM admissions
WHERE
  patient_id = '542'
  AND admission_date = (
    SELECT MAX(admission_date)
    FROM admissions
    WHERE patient_id = '542'
  )
  
--ANS3:
SELECT
  patient_id,
  nursing_unit_id,
  room,
  bed
FROM admissions
WHERE patient_id = 542
ORDER BY admission_date DESC
LIMIT 1

--ANS4:
SELECT
  patient_id,
  nursing_unit_id,
  room,
  bed
FROM admissions
GROUP BY patient_id
HAVING
  patient_id = 542
  AND max(admission_date)
  
-- MEDIUM-21:  Show the nursing_unit_id and count of admissions for each nursing_unit_id. Exclude the following nursing_unit_ids: 'CCU', 'OR', 'ICU', 'ER'.
--ANS1:
SELECT
  nursing_unit_id,
  COUNT(*) AS total_admissions
FROM admissions
WHERE
  nursing_unit_id NOT IN ('CCU', 'OR', 'ICU', 'ER')
GROUP BY nursing_unit_id;

--ANS2:
SELECT
  nursing_unit_id,
  count(*) count_of_admissions
FROM admissions
GROUP BY nursing_unit_id
HAVING
  nursing_unit_id NOT IN ('CCU', 'OR', 'ICU', 'ER');
  
-- MEDIUM-22: Show patient_id, attending_physician_id, and primary_diagnosis for admissions that match one of the two criteria:
-- 1. patient_id is an odd number and attending_physician_id is either 1, 5, or 19.
-- 2. attending_physician_id contains a 2 and the length of patient_id is 3 characters. '
SELECT
  patient_id,
  attending_physician_id,
  primary_diagnosis
FROM admissions
WHERE
  (
    attending_physician_id IN (1, 5, 19)
    AND patient_id % 2 != 0
  )
  OR 
  (
    attending_physician_id LIKE '%2%'
    AND len(patient_id) = 3
  )
  
-- MEDIUM-23: Display every patient's first_name. Order the list by the length of each name and then by alphbetically

select first_name
FROM patients
ORDER BY LENGTH(first_name) ASC, first_name ASC;

-- HARD-1: Show all of the patients grouped into weight groups. Show the total amount of patients in each weight group. Order the list by the weight group decending.
-- For example, if they weight 100 to 109 they are placed in the 100 weight group, 110-119 = 110 weight group, etc.

--Ans1:
SELECT count(patient_id) as patients_in_group, 

( case 
  when(weight >= 0 and weight < 10) then 0
  when(weight >= 10 and weight < 20) then 10
  when(weight >= 20 and weight < 30) then 20
  when(weight >= 30 and weight < 40) then 30
  when(weight >= 40 and weight < 50) then 40
  when(weight >= 50 and weight < 60) then 50
  when(weight >= 60 and weight < 70) then 60
  when(weight >= 70 and weight < 80) then 70
  when(weight >= 80 and weight < 90) then 80
  when(weight >= 90 and weight < 100) then 90
  when(weight >= 100 and weight < 110) then 100
  when(weight >= 110 and weight < 120) then 110
  when(weight >= 120 and weight < 130) then 120
  when(weight >= 130 and weight < 140) then 130
  end
) 
as weight_group
from patients
group by weight_group
order by weight_group desc;

--Ans2:
SELECT COUNT(*) AS patients_in_group, weight/10*10 AS weight_group FROM patients
  GROUP BY weight_group
  ORDER BY weight_group desc;
  -- Because weight is a integer, dividing by 10 automatically rounds down. We then multiply it by 10 to make their weight rounded down to the tens.
  -- If weight was a decimal number we could solve this by doing FLOOR(weight/10)*10
  
--Ans3:
SELECT COUNT() AS patients_in_group, 
       weight/10*10 AS weight_group
FROM patients
GROUP BY weight_group
GROUP BY weight_group DESC;
  
-- HARD-2: Show patient_id, weight, height, isObese from the patients table.
-- Display isObese as a boolean 0 or 1.
-- Obese is defined as weight(kg)/(height(m)2) >= 30.
-- weight is in units kg.
-- height is in units cm.

-- Ans1
SELECT patient_id, weight, height, 
( cast(weight as float)/power((cast(height as float)/100),2) >= 30) as isObese
from patients;

-- Ans2
SELECT patient_id, weight, height, 
  (CASE 
      WHEN weight/(POWER(height/100.0,2)) >= 30 THEN
          1
      ELSE
          0
      END) AS isObese
FROM patients;

-- HARD-3: Show patient_id, first_name, last_name, and attending physician's specialty.
-- Show only the patients who has a primary_diagnosis as 'Dementia' and the physician's first name is 'Lisa'
-- Check patients, admissions, and physicians tables for required information.
-- Ans1  
SELECT pt.patient_id, pt.first_name, pt.last_name, phy.specialty
from ((admissions as adm inner join patients as pt on adm.patient_id is pt.patient_id )
       inner join physicians as phy on adm.attending_physician_id is phy.physician_id)
where adm.primary_diagnosis = 'Dementia' and phy.first_name is 'Lisa';

-- Ans2
SELECT p.patient_id, p.first_name as patient_first_name, p.last_name as patient_last_name, ph.specialty as attending_physician_specialty FROM patients p
  JOIN admissions a ON a.patient_id = p.patient_id
  JOIN physicians ph ON ph.physician_id = a.attending_physician_id
  WHERE primary_diagnosis = 'Dementia' AND ph.first_name = 'Lisa';

-- HARD-4: All patients who have gone through admissions, can see their medical documents on our site. Those patients are given a temporary password after their first admission. Show the patient_id and temp_password.
-- The password must be the following, in order:
-- 1. patient_id
-- 2. the numerical length of patient's last_name
-- 3. year of patient's birth_date

--Ans1: 
select distinct(pt.patient_id), concat(pt.patient_id,length(pt.last_name),year(pt.birth_date)) as temp_password
from admissions as adm inner join patients as pt on adm.patient_id is pt.patient_id;
      
--Ans2:
SELECT DISTINCT P.patient_id, CONCAT(P.patient_id, LEN(last_name), YEAR(birth_date)) AS temp_password
FROM patients P INNER JOIN admissions A ON A.patient_id = P.patient_id
     
-- HARD-5: Each admission costs $50 for patients without insurance, and $10 for patients with insurance. All patients with an even patient_id have insurance.
-- Give each patient a 'Yes' if they have insurance, and a 'No' if they don't have insurance. Add up the admission_total cost for each has_insurance group. 

--Ans1:
select 
     ( case
         when patient_id % 2 is 0 then 'Yes'
         when patient_id % 2 is not 0 then 'No'
     end ) as has_insurance, 
     (sum( case
         when patient_id % 2 is 0 then 10
         when patient_id % 2 is not 0 then 50
     end )) as cost_after_insurance
from admissions
group by has_insurance;

--Ans2
SELECT 
CASE WHEN patient_id % 2 = 0 Then 
    'Yes'
ELSE 
    'No' 
END as has_insurance,
SUM(CASE WHEN patient_id % 2 = 0 Then 
    10
ELSE 
    50 
END) as cost_after_insurance
FROM admissions 
GROUP BY has_insurance;


-- HARD-6: Show the provinces that has more patients identified as 'M' than 'F'. Must only show full province_name.
--Ans1
SELECT
pr.province_name
FROM
patients as pa
JOIN
provinces as pr
ON
pa.province_id = pr.province_id
GROUP BY
pr.province_name
HAVING
COUNT(CASE WHEN gender = 'M' THEN 1 END) > COUNT(CASE WHEN gender = 'F' THEN 1 END);

--Ans2
select 
pv.province_name
from 
patients as pt 
join 
provinces as pv
on 
pt.province_id = pv.province_id
group by 
pt.province_id
having 
count(case when gender is 'M' then 1 end) > count(case when gender is 'F' then 1 end);


-- HARD-7: We are looking for a specific patient. Pull all columns for the patient who matches the following criteria:
-- - First_name contains an 'r' after the first two letters.
-- - Identifies their gender as 'F'
-- - Born in February, May, or December
-- - Their weight would be between 60kg and 80kg
-- - Their patient_id is an odd number
-- - They are from the city 'Halifax'

-- Ans1:
SELECT * FROM patients
WHERE
first_name LIKE '__r%'
AND gender = 'F'
AND MONTH(birth_date) IN (2,5,12)
AND weight BETWEEN 60 AND 80
AND patient_id % 2 = 1
AND city = 'Halifax';

-- HARD-8: Show the percent of patients that have 'M' as their gender. Round the answer to the nearest hundreth number and in percent form.
-- Ans1:
select 
concat(
  Round(
    cast(count(case when gender is "M" then 1 end) as float) 
    / 
    cast(count(*) as float) * 100, 2),"%") as percent_of_male_patients from patients;

-- Ans2:
SELECT
CONCAT(ROUND((SELECT COUNT(*) FROM patients WHERE gender = 'M')/(CAST(COUNT(*) as float)),4)*100,'%') as percent_of_male_patients
FROM patients;

-- HARD-9: Show the patient_id and total_spent for patients who spent over 150 in combined medication_cost. Sort by most total_spent to least total_spent.
-- ANS1: 
SELECT
  patient_id,
  SUM(medication_cost) AS total_spent
FROM unit_dose_orders udo
  JOIN medications me ON udo.medication_id = me.medication_id
GROUP BY patient_id
HAVING total_spent > 150
ORDER BY total_spent DESC;

-- HARD-10: Provide the description of each item, along with the total cost of the quantity on hand (rounded to the nearest whole dollar), 
-- and the associated primary vendor. Sort the output by the most spent to the least spent on inventory.
-- Ans1: 
SELECT
  i.item_description,
  ROUND(i.item_cost * i.quantity_on_hand, 0) as total_cost,
  v.vendor_name
FROM items i
  JOIN vendors v ON i.primary_vendor_id = v.vendor_id
GROUP BY i.item_description
ORDER BY total_cost DESC;

-- HARD-11: For each day display the total amount of admissions on that day. Display the amount changed from the previous date.
WITH admission_counts_table AS (
  SELECT admission_date, COUNT(patient_id) AS admission_count
  FROM admissions
  GROUP BY admission_date
  ORDER BY admission_date DESC
)
select
  admission_date, 
  admission_count, 
  admission_count - LAG(admission_count) OVER(ORDER BY admission_date) AS admission_count_change 
from admission_counts_table



























