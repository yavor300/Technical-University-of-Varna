-- This procedure calculates the total number of pages required to display products based
-- on user-specified filtering criteria and batch size. It takes into account the price
-- range, product name pattern, group name pattern, and desired batch size to determine
-- how many pages of products there would be if the products were divided into batches of the specified size.
--
-- Input Parameters:
--   :price_start_interval:
--     The minimum price in the desired price range.
--     If not specified, there's no minimum boundary.
--   :price_end_interval:
--     The maximum price in the desired price range.
--     If not specified, there's no maximum boundary.
--   :product_name_pattern:
--     A pattern for product names.
--     The procedure will count products whose names match this pattern.
--     If not specified, all product names are included.
--   :group_name_pattern:
--     A pattern for group names.
--     The procedure will count products that belong to groups whose names match this pattern.
--     If not specified, all group names are included.
--   :batch_start:
--     The starting number of the desired batch. If not specified, the default is 1.
--   :batch_end:
--     The ending number of the desired batch. If not specified, the default is 10.
--
-- Validations:
--   Price intervals should not be negative.
--     If either price_start_interval or price_end_interval is negative,
--     the message "Price intervals cannot be negative." will be displayed.
--   :price_start_interval should not exceed price_end_interval.
--     If this happens, a message "Start interval cannot be greater than end interval." will be shown.
--   Batch start and end values should be positive.
--     If not, the message "Batch start and end values should be positive." will be displayed.
--   :batch_start should not exceed :batch_end.
--     If this happens, a message "Batch start cannot be greater than batch end." will be shown.
--
-- Output:
--   The procedure outputs the total number of pages as a message in
--   the format "Total pages: X", where X is the calculated number of pages.
--
-- Error Handling:
--   In case of exceptions or errors, relevant error messages will be
--   displayed, providing information about the nature of the error.
CREATE OR REPLACE PROCEDURE get_total_pages(
  p_batch_start IN NUMBER DEFAULT 1,
  p_batch_end IN NUMBER DEFAULT 10,
  p_price_start_interval IN NUMBER DEFAULT NULL,
  p_price_end_interval IN NUMBER DEFAULT NULL,
  p_product_name_pattern IN VARCHAR2 DEFAULT NULL,
  p_group_name_pattern IN VARCHAR2 DEFAULT NULL
) AS
  v_batch_size  NUMBER;
  v_total_pages NUMBER;
  v_start_row   NUMBER := NVL(p_batch_start, 1);
  v_end_row     NUMBER := NVL(p_batch_end, 10);
BEGIN

  -- Validations for price intervals
  validate_price_intervals(p_price_start_interval, p_price_end_interval);

  -- Validations for batch numbers
  validate_batch_numbers(v_start_row, v_end_row);

  -- Calculate batch size
  v_batch_size := v_end_row - v_start_row + 1;

  SELECT CEIL(COUNT(*)/v_batch_size) INTO v_total_pages
  FROM product p
  JOIN inventory i ON p.product_id = i.product_id
  JOIN "GROUP" g ON p.group_id = g.group_id
  WHERE 
    filter_price(i.price, p_price_start_interval, p_price_end_interval) = 1
    AND
    filter_product_name(p.name, p_product_name_pattern) = 1
    AND
    filter_group_name(g.name, p_group_name_pattern) = 1;

  DBMS_OUTPUT.PUT_LINE('Total pages: ' || v_total_pages);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END get_total_pages;
/