-- Clear existing data first
DELETE FROM account;
DELETE FROM customer;

-- Insert Customers (IDs will auto-generate)
INSERT INTO customer (first_name, last_name, email) VALUES
('Alice', 'Johnson', 'alice@example.com'),
('Bob', 'Smith', 'bob@example.com');

-- Insert Accounts linked to customers
-- Note: H2 auto-generated IDs start from 1 by default, so customer IDs will be 1, 2, ...
INSERT INTO account (account_number, account_type, balance, customer_id) VALUES
('ACC1001', 'SAVINGS', 1000.00, 1),
('ACC1002', 'CURRENT', 2500.50, 1),
('ACC1003', 'SAVINGS', 3000.75, 2);