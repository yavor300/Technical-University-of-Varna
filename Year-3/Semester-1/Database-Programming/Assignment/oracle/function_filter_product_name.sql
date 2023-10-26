-- The function provides a mechanism to match product names against a specific pattern. 
-- Given a product name and an optional matching pattern, the function evaluates 
-- if the product name conforms to the pattern. In the absence of a specified pattern, the product name matches itself. 
-- A return value of 1 signifies a match, while 0 indicates no match.
CREATE OR REPLACE FUNCTION filter_product_name(p_name VARCHAR2, p_product_name_pattern VARCHAR2 DEFAULT NULL) RETURN NUMBER IS
BEGIN
    IF (UPPER(p_name) LIKE '%' || UPPER(NVL(p_product_name_pattern, p_name)) || '%') THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
END filter_product_name;
/
