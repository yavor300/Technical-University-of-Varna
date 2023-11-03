CREATE OR REPLACE FUNCTION get_product_name(p_product_id IN product.product_id%TYPE) 
RETURN VARCHAR2 AS
  v_product_name product.name%TYPE;
BEGIN
  SELECT name INTO v_product_name FROM product WHERE product_id = p_product_id;
  RETURN v_product_name;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20001, 'Product with ID ' || p_product_id || ' does not exist.');
  WHEN OTHERS THEN
    RAISE;
END get_product_name;
/
