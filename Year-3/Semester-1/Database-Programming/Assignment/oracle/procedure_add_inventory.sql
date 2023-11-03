CREATE OR REPLACE PROCEDURE add_inventory(
    p_quantity    IN inventory.quantity%TYPE,
    p_price       IN inventory.price%TYPE,
    p_import_date IN inventory.import_date%TYPE,
    p_product_id  IN inventory.product_id%TYPE
) AS
  v_product_count NUMBER;
BEGIN

  SELECT COUNT(*)
  INTO v_product_count
  FROM product
  WHERE product_id = p_product_id;

  IF v_product_count = 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Product does not exist.');
  END IF;

  IF p_price <= 0 THEN
    RAISE_APPLICATION_ERROR(-20002, 'Price must be a positive number.');
  END IF;

  IF p_quantity < 0 THEN
    RAISE_APPLICATION_ERROR(-20003, 'Quantity cannot be negative.');
  END IF;

  INSERT INTO inventory (quantity, price, import_date, product_id)
  VALUES (p_quantity, p_price, p_import_date, p_product_id);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_inventory;
/
