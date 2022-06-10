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
SELECT COUNT(*) as total_patients FROM patients
WHERE YEAR(birth_date) = 2010;

-- EASY-10: Show the first_name, last_name, and height of the patient with the greatest height.
SELECT first_name, last_name, MAX(height) as height
FROM patients;

-- EASY-11: Show all columns for patients who have one of the following patient_ids: 1,45,534,879,1000
SELECT * FROM patients 
WHERE patient_id IN (1,45,534,879,1000);

-- EASY-12: Show the total number of admissions
SELECT COUNT(*) as total_admissions
FROM admissions;

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
  
  


























