-- This function retrieves the total available quantity of a specific product from the inventory. 
-- It takes a product ID as an argument and sums up all quantities related to that product ID across 
-- all inventory entries. If the product does not exist in the inventory or has no stock, the function returns 0.
CREATE OR REPLACE FUNCTION get_total_available_qty(p_product_id NUMBER) RETURN NUMBER IS
    v_total_qty NUMBER;
BEGIN
    SELECT SUM(i.quantity)
    INTO v_total_qty
    FROM product p
    JOIN inventory i ON p.product_id = i.product_id
    WHERE p.product_id = p_product_id;
    
    RETURN NVL(v_total_qty, 0);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RAISE;
END get_total_available_qty;
/
