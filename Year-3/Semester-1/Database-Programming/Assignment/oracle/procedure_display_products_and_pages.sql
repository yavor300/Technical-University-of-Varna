-- The procedure serves a dual purpose.
-- Initially, it fetches and displays products based on the
-- given criteria which include
-- filtering by price range, product name, and group name. 
-- Subsequently, it calculates and outputs the total number of
-- pages these products span based on provided 
-- batch start and end numbers.
CREATE OR REPLACE PROCEDURE display_products_and_pages(
  p_batch_start IN NUMBER DEFAULT 1,
  p_batch_end IN NUMBER DEFAULT 10,
  p_price_start_interval IN NUMBER DEFAULT NULL,
  p_price_end_interval IN NUMBER DEFAULT NULL,
  p_product_name_pattern IN VARCHAR2 DEFAULT NULL,
  p_group_name_pattern IN VARCHAR2 DEFAULT NULL
) AS
BEGIN
  fetch_products(p_batch_start, p_batch_end, p_price_start_interval, p_price_end_interval, p_product_name_pattern, p_group_name_pattern);
  get_total_pages(p_batch_start, p_batch_end, p_price_start_interval, p_price_end_interval, p_product_name_pattern, p_group_name_pattern);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END display_products_and_pages;
/
