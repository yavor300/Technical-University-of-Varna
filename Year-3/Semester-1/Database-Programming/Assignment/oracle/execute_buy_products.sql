-- This PL/SQL anonymous block invokes the `buy_products` procedure. 
-- The block initializes arrays with product IDs and requested quantities, then calls the procedure 
-- to simulate a product purchase for a given customer and employee. If any error occurs during execution, 
-- an appropriate error message will be displayed.
DECLARE
  v_product_ids NUMBER_TABLE_TYPE := NUMBER_TABLE_TYPE(220, 110);
  v_requested_qtys NUMBER_TABLE_TYPE := NUMBER_TABLE_TYPE(200, 5);
BEGIN
  buy_products(
    p_product_ids => v_product_ids,
    p_requested_qtys => v_requested_qtys,
    p_customer_id => 100,
    p_employee_id => 110
  );
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/