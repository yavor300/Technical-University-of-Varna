CREATE OR REPLACE FUNCTION get_employee_id_by_phone(
  p_phone_number IN employee.phone_number%TYPE
) RETURN employee.employee_id%TYPE AS
  v_employee_id employee.employee_id%TYPE;
BEGIN
  SELECT employee_id
  INTO v_employee_id
  FROM employee
  WHERE phone_number = p_phone_number;

  RETURN v_employee_id;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20001, 'No employee found with the given phone number.');
  WHEN OTHERS THEN
    RAISE;
END get_employee_id_by_phone;
/
