-- This procedure facilitates the purchase of products. It validates the
-- sales permissions of the involved employee, creates a sales record, 
-- and updates the inventory based on the products purchased.
--
-- Parameters:
--   p_product_ids:    An array of product IDs that the customer wants to purchase.
--   p_requested_qtys: The corresponding array of quantities for each product ID.
--   p_customer_id:    The ID of the purchasing customer.
--   p_employee_id:    The ID of the employee processing the sale.
--
-- Outputs: 
--   Inserts a new record into the 'sale' table.
--   Inserts records into 'inventory_sale' table for each product purchased.
--   Outputs a success message to DBMS_OUTPUT.
--   Can raise application errors under various conditions (e.g., insufficient stock,
--   invalid employee permissions, etc.)
--
-- Exceptions:
--  Raises an error if a customer does not exist.
--  Raises an error if an employee doesn't have sales permissions.
--  Raises an error if there's insufficient stock for a product.
--  Raises an error if the product ID or employee ID is not found.
CREATE OR REPLACE PROCEDURE buy_products(
  p_product_ids      IN NUMBER_TABLE_TYPE,
  p_requested_qtys   IN NUMBER_TABLE_TYPE,
  p_customer_id      IN NUMBER,
  p_employee_id      IN NUMBER
) AS
  v_available_qty         NUMBER;
  v_sale_id               NUMBER;
  v_remaining_qty         NUMBER;
  v_current_inventory_qty INTEGER;
  v_current_inventory_id  INTEGER;
  v_employee_position     VARCHAR2(65);
  v_sold_product_output   VARCHAR2(1000);
BEGIN

  IF NOT customer_exists(p_customer_id) THEN
    RAISE_APPLICATION_ERROR(-20013, 'Customer ID not found.');
  END IF;

  IF NOT has_sales_permission(p_employee_id) THEN
    RAISE_APPLICATION_ERROR(-20012, 'This employee is not allowed to process sales.');
  END IF;

  add_sale(SYSDATE, p_customer_id, p_employee_id, v_sale_id);

  FOR i IN 1..p_product_ids.COUNT LOOP
    v_available_qty := get_total_available_qty(p_product_ids(i));

    IF v_available_qty < p_requested_qtys(i) THEN
      RAISE_APPLICATION_ERROR(-20010, 'Insufficient stock available for product ' || get_product_name(p_product_ids(i)));
    END IF;

    v_remaining_qty := p_requested_qtys(i);
    FOR rec IN (SELECT inventory_id, quantity FROM inventory WHERE product_id = p_product_ids(i) ORDER BY import_date ASC) LOOP
      IF v_remaining_qty <= 0 THEN EXIT; END IF;
      v_current_inventory_qty := LEAST(v_remaining_qty, rec.quantity);
      v_current_inventory_id := rec.inventory_id;

      add_inventory_sale(v_current_inventory_qty, v_current_inventory_id, v_sale_id);

      v_remaining_qty := v_remaining_qty - v_current_inventory_qty;
    END LOOP;
  END LOOP;

  DBMS_OUTPUT.PUT_LINE('Purchase processed successfully!');

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20011, 'Product ID or Employee ID not found.');
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END buy_products;
/
