-- This trigger is executed after a new record is inserted into the "inventory_sale" table.
-- It fetches the product's name and price from the associated inventory and then prints out 
-- the product's name, the sold quantity, individual price, and the total price for that sale.
CREATE OR REPLACE TRIGGER tr_after_insert_on_inventory_sale
AFTER INSERT ON inventory_sale
FOR EACH ROW
DECLARE
  v_product_name VARCHAR2(255);
  v_price NUMBER;
BEGIN
  SELECT p.name, i.price
  INTO v_product_name, v_price
  FROM product p
  JOIN inventory i ON i.product_id = p.product_id
  WHERE i.inventory_id = :NEW.inventory_id;

  DBMS_OUTPUT.PUT_LINE('Product: ' || v_product_name || 
                       ', Quantity: ' || TO_CHAR(:NEW.sold_quantity) || 
                       ', Price: ' || TO_CHAR(v_price) || 
                       ', Total: ' || TO_CHAR(:NEW.sold_quantity * v_price));
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred in trg_after_insert_on_sale: ' || SQLERRM);
    RAISE;
END;
/
