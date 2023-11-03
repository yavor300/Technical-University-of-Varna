CREATE OR REPLACE PROCEDURE add_product(
    p_name     IN product.name%TYPE,
    p_group_id IN product.group_id%TYPE
) AS
  v_product_count NUMBER;
  v_group_count NUMBER;
BEGIN

  SELECT COUNT(*)
  INTO v_product_count
  FROM product
  WHERE UPPER(name) = UPPER(p_name);

  IF v_product_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Product name already exists.');
  END IF;

  SELECT COUNT(*)
  INTO v_group_count
  FROM "GROUP"
  WHERE group_id = p_group_id;

  IF v_group_count = 0 THEN
    RAISE_APPLICATION_ERROR(-20002, 'Group ID does not exist.');
  END IF;

  INSERT INTO product (name, group_id) VALUES (p_name, p_group_id);
  DBMS_OUTPUT.PUT_LINE('New product added successfully.');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_product;
/
