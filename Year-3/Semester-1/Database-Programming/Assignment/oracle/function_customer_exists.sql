-- The function determines whether a customer, identified by a given customer ID,
-- exists in the database. It queries the `customer` table and checks for the presence
-- of the specified customer ID. The function returns `TRUE` if the customer exists and `FALSE` otherwise.
CREATE OR REPLACE FUNCTION customer_exists(p_customer_id IN NUMBER) RETURN BOOLEAN AS
  v_customer_count INTEGER;
BEGIN
  SELECT COUNT(*) 
  INTO v_customer_count
  FROM customer
  WHERE customer_id = p_customer_id;

  RETURN v_customer_count > 0;

EXCEPTION
  WHEN OTHERS THEN
    RAISE;
END customer_exists;
/
