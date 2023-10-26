-- This PL/SQL block retrieves and displays a list of products from the
-- database based on user-defined criteria such as price intervals,
-- product name patterns, and group name patterns. The results can also
-- be limited by specifying batch start and end values. The procedure joins
-- the product, inventory, and GROUP tables to provide a comprehensive view of
-- each product, including its ID, name, group name, price, available quantity, and import date.
--
-- Parameters:
--  :price_start_interval:
--    The minimum price in the desired price range.
--    If not specified, there's no minimum boundary.
--  :price_end_interval:
--    The maximum price in the desired price range.
--    If not specified, there's no maximum boundary.
--  :product_name_pattern:
--    A string pattern to filter products by name.
--    Products with names matching this pattern (case-insensitive)
--    will be retrieved. If unspecified, all product names will be considered.
--  :group_name_pattern:
--    A string pattern to filter products by their associated group name.
--    Products belonging to groups matching this pattern (case-insensitive) will be retrieved.
--    If not provided, all group names are considered.
--  :batch_start:
--    The starting row number for the batch of records to be retrieved.
--  :batch_end:
--    The ending row number for the batch of records to be retrieved.
--
-- Validations:
--   Both :price_start_interval and :price_end_interval should not be negative.
--   If either is negative, a message "Price intervals cannot be negative." will be displayed.
--   :price_start_interval should not exceed :price_end_interval.
--   If this happens, a message "Start interval cannot be greater than end interval." will be shown.
-- Both :batch_start and :batch_end should not be negative.
--   If either is negative, a message "Batch start and end values should be positive." will be displayed.
--   :batch_start should not exceed :batch_end.
--   If this happens, a message "Batch start cannot be greater than batch end." will be shown.
--
-- Output:
--   Products that fit within the specified price range will be displayed,
--   ordered by their product IDs and import dates.
--   If no products are found within the range, the message
--   "No records found for the given price interval." is shown.
--
-- Exceptions:
--   In case of any exceptions, the cursor, if open, will be closed, and the exception will be logged.
CREATE OR REPLACE PROCEDURE fetch_products(
    p_batch_start IN NUMBER DEFAULT 1,
    p_batch_end IN NUMBER DEFAULT 10,
    p_price_start_interval IN NUMBER DEFAULT NULL,
    p_price_end_interval IN NUMBER DEFAULT NULL,
    p_product_name_pattern IN VARCHAR2 DEFAULT NULL,
    p_group_name_pattern IN VARCHAR2 DEFAULT NULL
) AS
  v_product_id         NUMBER;
  v_product_name       VARCHAR2(255);
  v_group_name         VARCHAR2(255);
  v_price              NUMBER;
  v_available_quantity INTEGER;
  v_import_date        DATE;
  v_has_records        BOOLEAN := FALSE;
  v_start_row          NUMBER := NVL(p_batch_start, 1);
  v_end_row            NUMBER := NVL(p_batch_end, 10);

  CURSOR product_cursor IS
    SELECT 
      subq.product_id, 
      subq.name AS product_name, 
      subq.group_name, 
      subq.price,
      subq.quantity AS available_quantity,
      subq.import_date
    FROM 
      (SELECT
        p.product_id, 
        p.name, 
        g.name AS group_name, 
        i.price,
        i.quantity,
        i.import_date,
        ROW_NUMBER() OVER (ORDER BY p.product_id ASC, i.import_date ASC) AS row_num
      FROM 
        product p
      JOIN 
        inventory i ON p.product_id = i.product_id
      JOIN 
        "GROUP" g ON p.group_id = g.group_id
      WHERE 
        filter_price(i.price, p_price_start_interval, p_price_end_interval) = 1
        AND
        filter_product_name(p.name, p_product_name_pattern) = 1
        AND
        filter_group_name(g.name, p_group_name_pattern) = 1
      ) subq
    WHERE 
      subq.row_num BETWEEN v_start_row AND v_end_row;
BEGIN
  -- Validations for price intervals
  validate_price_intervals(p_price_start_interval, p_price_end_interval);

  -- Validations for batch numbers
  validate_batch_numbers(v_start_row, v_end_row);

  OPEN product_cursor;

  LOOP
    FETCH product_cursor INTO v_product_id, v_product_name, v_group_name, v_price, v_available_quantity, v_import_date;
    EXIT WHEN product_cursor%NOTFOUND;

    v_has_records := TRUE;
    DBMS_OUTPUT.PUT_LINE('Product ID: ' || v_product_id || ', Product Name: ' || v_product_name || ', Group Name: ' || v_group_name || ', Price: ' || v_price || ', Available Quantity: ' || v_available_quantity || ', Import Date: ' || TO_CHAR(v_import_date, 'MM/DD/YYYY'));
  END LOOP;

  CLOSE product_cursor;

  IF NOT v_has_records THEN
    DBMS_OUTPUT.PUT_LINE('No records found for the provided criteria.');
  END IF;
EXCEPTION
  WHEN OTHERS THEN
    IF product_cursor%ISOPEN THEN
      CLOSE product_cursor;
    END IF;
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END fetch_products;
/