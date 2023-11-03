CREATE OR REPLACE FUNCTION get_customer_id_by_phone(
  p_phone_number IN customer.phone_number%TYPE
) RETURN customer.customer_id%TYPE AS
  v_customer_id customer.customer_id%TYPE;
BEGIN
  SELECT customer_id
  INTO v_customer_id
  FROM customer
  WHERE phone_number = p_phone_number;

  RETURN v_customer_id;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20001, 'No customer found with the given phone number.');
  WHEN OTHERS THEN
    RAISE;
END get_customer_id_by_phone;
/
