CREATE OR REPLACE PROCEDURE add_customer(
  p_first_name   IN customer.first_name%TYPE,
  p_last_name    IN customer.last_name%TYPE,
  p_phone_number IN customer.phone_number%TYPE
) AS
  v_phone_count NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO v_phone_count
  FROM (
    SELECT phone_number FROM customer
    UNION
    SELECT phone_number FROM employee
  )
  WHERE phone_number = p_phone_number;

  IF v_phone_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'The phone number is already in use.');
  END IF;

  INSERT INTO customer (first_name, last_name, phone_number)
  VALUES (p_first_name, p_last_name, p_phone_number);

  DBMS_OUTPUT.PUT_LINE('New customer added successfully.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_customer;
/
