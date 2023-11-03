CREATE OR REPLACE PROCEDURE add_employee(
  p_first_name   IN employee.first_name%TYPE,
  p_last_name    IN employee.last_name%TYPE,
  p_phone_number IN employee.phone_number%TYPE,
  p_position_id  IN employee.position_id%TYPE
) AS
  v_phone_count  NUMBER;
  v_pos_count    NUMBER;
BEGIN

  SELECT COUNT(*)
  INTO v_phone_count
  FROM (
    SELECT phone_number FROM employee
    UNION
    SELECT phone_number FROM customer
  )
  WHERE phone_number = p_phone_number;

  IF v_phone_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20000, 'Phone number already exists.');
  END IF;

  SELECT COUNT(*)
  INTO v_pos_count
  FROM position
  WHERE position_id = p_position_id;

  IF v_pos_count = 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Position ID does not exist.');
  END IF;

  INSERT INTO employee (first_name, last_name, phone_number, position_id)
  VALUES (p_first_name, p_last_name, p_phone_number, p_position_id);

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_employee;
/
