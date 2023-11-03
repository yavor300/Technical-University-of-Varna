-- Populate the sale table.
CREATE OR REPLACE PROCEDURE add_sale(
    p_date         IN sale."date"%TYPE,
    p_customer_id  IN sale.customer_id%TYPE,
    p_employee_id  IN sale.employee_id%TYPE,
    p_sale_id      OUT sale.sale_id%TYPE
) AS
  v_customer_exists BOOLEAN;
  v_employee_exists BOOLEAN;
BEGIN

  v_customer_exists := customer_exists(p_customer_id);

  IF NOT v_customer_exists THEN
    RAISE_APPLICATION_ERROR(-20003, 'Customer ID does not exist.');
  END IF;

  v_employee_exists := has_sales_permission(p_employee_id);

  IF NOT v_employee_exists THEN
    RAISE_APPLICATION_ERROR(-20004, 'Employee ID does not exist or is not permitted to process sales.');
  END IF;

  INSERT INTO sale ("date", customer_id, employee_id) VALUES (p_date, p_customer_id, p_employee_id) RETURNING sale_id INTO p_sale_id;

  DBMS_OUTPUT.PUT_LINE('New sale added successfully.');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_sale;
/
