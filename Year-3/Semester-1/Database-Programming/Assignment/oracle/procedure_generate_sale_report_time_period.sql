-- Generates a sales report for a specific date range and batch interval.
-- The report displays detailed information about each sale, including
-- the sale ID, sale date, product name, quantity sold, unit price, and total amount.
-- 
-- Parameters:
--   p_start_date    : The starting date for the report. Defaults to one month before the current date.
--   p_end_date      : The ending date for the report. Defaults to the current date.
--   p_batch_start   : Starting row number for batch processing. Defaults to 1.
--   p_batch_end     : Ending row number for batch processing. Defaults to 10.
--
-- Output:
--   Displays the sales report using DBMS_OUTPUT.
--
-- Exceptions:
--   The procedure uses validate_batch_numbers and validate_dates to ensure correct
--   batch numbers and dates. Any validation error or database error will be displayed.
CREATE OR REPLACE PROCEDURE generate_sale_report(
  p_start_date  IN DATE DEFAULT TRUNC(ADD_MONTHS(SYSDATE, -1)),
  p_end_date    IN DATE DEFAULT TRUNC(SYSDATE),
  p_batch_start IN NUMBER DEFAULT 1,
  p_batch_end   IN NUMBER DEFAULT 10
) AS
  v_start_date    DATE := NVL(p_start_date, TRUNC(ADD_MONTHS(SYSDATE, -1)));
  v_end_date      DATE := NVL(p_end_date, TRUNC(SYSDATE));
  v_start_row     NUMBER := NVL(p_batch_start, 1);
  v_end_row       NUMBER := NVL(p_batch_end, 10);
  v_sale_id       NUMBER;
  v_sale_date     DATE;
  v_product_name  VARCHAR(255);
  v_sold_quantity NUMBER;
  v_unit_price    NUMBER;
  v_total         NUMBER;

  CURSOR sale_cursor IS
    SELECT 
      subq.sale_id, 
      subq.sale_date,
      subq.product_name, 
      subq.sold_quantity, 
      subq.unit_price,
      subq.total
    FROM 
      (SELECT 
          s.sale_id,
          s."date" AS sale_date,
          p.name AS product_name,
          isale.sold_quantity,
          i.price AS unit_price,
          (i.price * isale.sold_quantity) AS total,
          ROW_NUMBER() OVER (ORDER BY s."date" ASC, s.sale_id ASC) AS row_num
        FROM sale s
        JOIN inventory_sale isale ON s.sale_id = isale.sale_id
        JOIN inventory i ON i.inventory_id = isale.inventory_id
        JOIN product p ON p.product_id = i.product_id
        WHERE s."date" BETWEEN v_start_date AND v_end_date
      ) subq
    WHERE 
      subq.row_num BETWEEN v_start_row AND v_end_row;

BEGIN
  validate_batch_numbers(v_start_row, v_end_row);
  validate_dates(v_start_date, v_end_date);

  DBMS_OUTPUT.PUT_LINE('Sale report: ' || TO_CHAR(v_start_date, 'DD-MON-YYYY') || ' to ' || TO_CHAR(v_end_date, 'DD-MON-YYYY'));

  OPEN sale_cursor;
  LOOP
    FETCH sale_cursor INTO v_sale_id, v_sale_date, v_product_name, v_sold_quantity, v_unit_price, v_total;
    EXIT WHEN sale_cursor%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE(
      'Sale ID: ' || v_sale_id || ', ' || 
      'Sale date: ' || TO_CHAR(v_sale_date, 'DD-MON-YYYY') || ', ' || 
      'Product name: ' || v_product_name || ', ' || 
      'Sold quantity: ' || v_sold_quantity || ', ' || 
      'Unit price: ' || TO_CHAR(v_unit_price, '9999.99') || ', ' || 
      'Total: ' || TO_CHAR(v_total, '9999.99')
    );

  END LOOP;
  CLOSE sale_cursor;

EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error generating sale report: ' || SQLERRM);
END generate_sale_report;
/
