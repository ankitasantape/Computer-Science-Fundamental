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


