-- The function determines if an employee, identified by their employee_id,
-- has the necessary permissions to process sales. The function retrieves
-- the employee's position from the database and checks if it matches any of the predefined
-- roles that are allowed to handle sales. If the employee holds any of these roles,
-- the function returns TRUE; otherwise, it returns FALSE.
-- In the event the provided employee_id does not exist,
-- an error is raised indicating that the employee ID was not found.
CREATE OR REPLACE FUNCTION has_sales_permission(p_employee_id IN NUMBER) RETURN BOOLEAN AS
  v_employee_position VARCHAR2(65);
BEGIN
  SELECT pos.name 
  INTO v_employee_position 
  FROM employee emp
  JOIN position pos ON emp.position_id = pos.position_id
  WHERE emp.employee_id = p_employee_id;

  IF v_employee_position IN ('Manager', 'Salesperson', 'Assistant manager', 'Cashier') THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20011, 'Employee ID not found.');
  WHEN OTHERS THEN
    RAISE;
END has_sales_permission;
/
