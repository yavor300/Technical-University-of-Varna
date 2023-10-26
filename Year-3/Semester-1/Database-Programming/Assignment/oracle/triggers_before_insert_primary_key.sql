-- The triggers below are designed to automate the generation of unique identifiers
-- for records in their respective tables. Before a new record is inserted, the trigger
-- fetches the next value from the corresponding sequence and assigns it to the record's primary key.

-- Trigger for the customer table: Assigns a unique customer_id before insertion
CREATE OR REPLACE TRIGGER customer_bir 
BEFORE INSERT ON customer 
FOR EACH ROW 
BEGIN
  :NEW.customer_id := customer_seq.NEXTVAL;
END;
/

-- Trigger for the employee table: Assigns a unique employee_id before insertion
CREATE OR REPLACE TRIGGER employee_bir 
BEFORE INSERT ON employee 
FOR EACH ROW 
BEGIN
  :NEW.employee_id := employee_seq.NEXTVAL;
END;
/

-- Trigger for the group table: Assigns a unique group_id before insertion
CREATE OR REPLACE TRIGGER group_bir 
BEFORE INSERT ON "GROUP" 
FOR EACH ROW 
BEGIN
  :NEW.group_id := group_seq.NEXTVAL;
END;
/

-- Trigger for the inventory table: Assigns a unique inventory_id before insertion
CREATE OR REPLACE TRIGGER inventory_bir 
BEFORE INSERT ON inventory 
FOR EACH ROW 
BEGIN
  :NEW.inventory_id := inventory_seq.NEXTVAL;
END;
/

-- Trigger for the inventory_sale table: Assigns a unique inventory_sale_id before insertion
CREATE OR REPLACE TRIGGER inventory_sale_bir 
BEFORE INSERT ON inventory_sale 
FOR EACH ROW 
BEGIN
  :NEW.inventory_sale_id := inventory_sale_seq.NEXTVAL;
END;
/

-- Trigger for the position table: Assigns a unique position_id before insertion
CREATE OR REPLACE TRIGGER position_bir 
BEFORE INSERT ON position 
FOR EACH ROW 
BEGIN
  :NEW.position_id := position_seq.NEXTVAL;
END;
/

-- Trigger for the product table: Assigns a unique product_id before insertion
CREATE OR REPLACE TRIGGER product_bir 
BEFORE INSERT ON product 
FOR EACH ROW 
BEGIN
  :NEW.product_id := product_seq.NEXTVAL;
END;
/

-- Trigger for the sale table: Assigns a unique sale_id before insertion
CREATE OR REPLACE TRIGGER sale_bir 
BEFORE INSERT ON sale 
FOR EACH ROW 
BEGIN
  :NEW.sale_id := sale_seq.NEXTVAL;
END;
/

