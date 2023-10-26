-- This trigger is fired after a new sale record is inserted into the "sale" table.
-- It fetches the related employee and customer information based on the newly inserted sale record.
-- The trigger then prints out the sale date, employee's name, position, customer's name, and phone number.
CREATE OR REPLACE TRIGGER tr_after_insert_on_sale
AFTER INSERT ON sale
FOR EACH ROW
DECLARE
  v_employee_first_name VARCHAR2(255);
  v_employee_last_name VARCHAR2(255);
  v_employee_position VARCHAR2(255);
  v_customer_first_name VARCHAR2(255);
  v_customer_last_name VARCHAR2(255);
  v_customer_phone VARCHAR2(255);
BEGIN
  SELECT e.first_name, e.last_name, p.name, c.first_name, c.last_name, c.phone_number
  INTO v_employee_first_name, v_employee_last_name, v_employee_position,
    v_customer_first_name, v_customer_last_name, v_customer_phone
  FROM employee e
  JOIN position p ON e.position_id = p.position_id
  JOIN customer c ON c.customer_id = :NEW.customer_id
  WHERE e.employee_id = :NEW.employee_id;
  DBMS_OUTPUT.PUT_LINE('Sale Date: ' || TO_CHAR(:NEW."date") || 
                       ', Employee: ' || v_employee_first_name || ' ' || v_employee_last_name || 
                       ', Position: ' || v_employee_position || 
                       ', Customer: ' || v_customer_first_name || ' ' || v_customer_last_name ||
                       ', Phone: ' || v_customer_phone);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred in trg_after_insert_on_sale: ' || SQLERRM);
END;
/
