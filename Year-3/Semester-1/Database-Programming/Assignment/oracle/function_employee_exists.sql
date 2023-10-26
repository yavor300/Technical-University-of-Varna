-- This function checks whether an employee with the given ID exists in the employee table.
-- It returns TRUE if the employee exists, and FALSE otherwise.
CREATE OR REPLACE FUNCTION employee_exists(
  p_employee_id IN NUMBER
) RETURN BOOLEAN AS
  v_count NUMBER(1);
BEGIN
  SELECT COUNT(*)
  INTO v_count
  FROM employee
  WHERE employee_id = p_employee_id;

  IF v_count > 0 THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
EXCEPTION
  WHEN OTHERS THEN
    RAISE;
END employee_exists;
/
