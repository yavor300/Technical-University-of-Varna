-- The following sequences are used to generate unique identifiers
-- for various tables in the database. 
-- Each sequence starts at 100 and increments by 10.
-- These sequences ensure that each record in the associated table receives
-- a distinct and predictable ID value.

-- Sequence for the customer table
CREATE SEQUENCE customer_seq START WITH 100 INCREMENT BY 10;

-- Sequence for the employee table
CREATE SEQUENCE employee_seq START WITH 100 INCREMENT BY 10;

-- Sequence for the group table
CREATE SEQUENCE group_seq START WITH 100 INCREMENT BY 10;

-- Sequence for the inventory table
CREATE SEQUENCE inventory_seq START WITH 100 INCREMENT BY 10;

-- Sequence for the inventory_sale table
CREATE SEQUENCE inventory_sale_seq START WITH 100 INCREMENT BY 10;

-- Sequence for the position table
CREATE SEQUENCE position_seq START WITH 100 INCREMENT BY 10;

-- Sequence for the product table
CREATE SEQUENCE product_seq START WITH 100 INCREMENT BY 10;

-- Sequence for the sale table
CREATE SEQUENCE sale_seq START WITH 100 INCREMENT BY 10;