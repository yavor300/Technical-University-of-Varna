-- This procedure generates a sale report for a specific customer, detailing each sale transaction
-- including products, quantities, and total amounts. The report is ordered by the sale date.
-- Pagination can be applied to the report by providing starting and ending batch numbers.
--
-- Parameters:
--   p_phone_number: The phone number of the customer for whom the report will be generated.
--   p_batch_start: The starting batch number for pagination (default is 1).
--   p_batch_end:   The ending batch number for pagination (default is 10).
--
-- Errors:
--   If the provided customer does not exist in the system, an error will be raised.
--   Any other unexpected errors will be captured and displayed using DBMS_OUTPUT.
CREATE OR REPLACE PROCEDURE generate_customer_sale_report(
  p_phone_number IN VARCHAR2,
  p_batch_start IN NUMBER DEFAULT 1,
  p_batch_end   IN NUMBER DEFAULT 10
) AS
  v_start_row           NUMBER := NVL(p_batch_start, 1);
  v_end_row             NUMBER := NVL(p_batch_end, 10);
  v_sale_id             NUMBER;
  v_sale_date           DATE;
  v_customer_first_name VARCHAR(255);
  v_customer_last_name  VARCHAR(255);
  v_products            VARCHAR(255);
  v_sale_total          NUMBER;
  v_sales_found         BOOLEAN := FALSE;

  CURSOR customer_sale_cursor IS
    SELECT 
      subq.sale_id, 
      subq.sale_date,
      subq.customer_first_name,
      subq.customer_last_name,
      LISTAGG(subq.product_name || ' (Qty: ' || subq.sold_quantity || ', Unit Price: ' || subq.unit_price || ')', '; ') 
        WITHIN GROUP (ORDER BY subq.product_name) AS products,
    SUM(subq.total) AS sale_total
    FROM 
      (SELECT 
          s.sale_id,
          s."date" AS sale_date,
          c.first_name as customer_first_name,
          c.last_name as customer_last_name,
          p.name AS product_name,
          isale.sold_quantity,
          i.price AS unit_price,
          (i.price * isale.sold_quantity) AS total,
          ROW_NUMBER() OVER (ORDER BY s."date" ASC, s.sale_id ASC) AS row_num
        FROM sale s
        JOIN inventory_sale isale ON s.sale_id = isale.sale_id
        JOIN inventory i ON i.inventory_id = isale.inventory_id
        JOIN product p ON p.product_id = i.product_id
        JOIN customer c ON s.customer_id = c.customer_id
        WHERE c.phone_number = p_phone_number
        ORDER BY s."date"
      ) subq
    WHERE subq.row_num BETWEEN v_start_row AND v_end_row
    GROUP BY subq.sale_id, subq.sale_date, subq.customer_first_name, subq.customer_last_name;


BEGIN
  validate_batch_numbers(v_start_row, v_end_row);

  IF NOT customer_exists(get_customer_id_by_phone(p_phone_number)) THEN
    RAISE_APPLICATION_ERROR(-20013, 'This customer does not exist!');
  END IF;

  OPEN customer_sale_cursor;
  LOOP
    FETCH customer_sale_cursor INTO v_sale_id, v_sale_date, v_customer_first_name, v_customer_last_name,
                          v_products, v_sale_total;
    EXIT WHEN customer_sale_cursor%NOTFOUND;

    v_sales_found := TRUE;

    DBMS_OUTPUT.PUT_LINE(
      'Sale date: ' || TO_CHAR(v_sale_date, 'DD-MON-YYYY') || ', ' || 
      'Customer fist name: ' || v_customer_first_name || ', ' ||
      'Customer last name: ' || v_customer_last_name || ', ' ||
      'Products: ' || v_products || ', ' || 
      'Total Sale Value: ' || TO_CHAR(v_sale_total, '9999.99')
    );

  END LOOP;
  CLOSE customer_sale_cursor;

  IF NOT v_sales_found THEN
    DBMS_OUTPUT.PUT_LINE('No sales found for customer with phone number: ' || p_phone_number);
  END IF;

EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error generating sale report: ' || SQLERRM);
END generate_customer_sale_report;
/
