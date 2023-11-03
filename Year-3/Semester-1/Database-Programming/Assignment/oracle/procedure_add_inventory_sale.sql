CREATE OR REPLACE PROCEDURE add_inventory_sale(
    p_sold_quantity IN inventory_sale.sold_quantity%TYPE,
    p_inventory_id  IN inventory_sale.inventory_id%TYPE,
    p_sale_id       IN inventory_sale.sale_id%TYPE
) AS
  v_inventory_count NUMBER;
  v_sale_count      NUMBER;
BEGIN

  IF p_sold_quantity <= 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Sold quantity must be a positive number.');
  END IF;

  SELECT COUNT(*)
  INTO v_inventory_count
  FROM inventory
  WHERE inventory_id = p_inventory_id;

  IF v_inventory_count = 0 THEN
    RAISE_APPLICATION_ERROR(-20002, 'Inventory ID does not exist.');
  END IF;

  SELECT COUNT(*)
  INTO v_sale_count
  FROM sale
  WHERE sale_id = p_sale_id;

  IF v_sale_count = 0 THEN
    RAISE_APPLICATION_ERROR(-20003, 'Sale ID does not exist.');
  END IF;

  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id)
  VALUES (p_sold_quantity, p_inventory_id, p_sale_id);

  DBMS_OUTPUT.PUT_LINE('Inventory sale record added successfully.');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_inventory_sale;
/
