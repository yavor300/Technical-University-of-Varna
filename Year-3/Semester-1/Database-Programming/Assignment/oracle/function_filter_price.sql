-- The function aids in identifying products within a specified price range.
-- It takes in a product's price and optional price interval boundaries.
-- The function assesses whether the product's price falls within these bounds.
-- If no bounds are specified, the product's price defaults to matching itself.
-- A return value of 1 represents a successful match, whereas 0 denotes no match.
CREATE OR REPLACE FUNCTION filter_price(i_price NUMBER, p_price_start_interval NUMBER DEFAULT NULL, p_price_end_interval NUMBER DEFAULT NULL) RETURN NUMBER IS
BEGIN
    IF (i_price >= NVL(p_price_start_interval, i_price) AND i_price <= NVL(p_price_end_interval, i_price)) THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
END filter_price;
/
