-- Generates a sales report for a specified employee, presenting details about
-- sales made, products sold, and the associated quantities and prices.
-- The report will also display the sales date, employee's name, position, and product information.
-- Results can be limited to a specified range (batch) for pagination or batching purposes.
-- Raises an error if the employee does not exist or if they do not have the permission to process sales.
-- If no sales are found for the employee, a corresponding message is displayed.
--
-- Parameters:
-- p_employee_id: The ID of the employee for whom the report is generated.
-- p_batch_start: The starting row number for the batch (default is 1).
-- p_batch_end  : The ending row number for the batch (default is 10).
CREATE OR REPLACE PROCEDURE generate_employee_sale_report(
  p_employee_id IN NUMBER,
  p_batch_start IN NUMBER DEFAULT 1,
  p_batch_end   IN NUMBER DEFAULT 10
) AS
  v_start_row           NUMBER := NVL(p_batch_start, 1);
  v_end_row             NUMBER := NVL(p_batch_end, 10);
  v_sale_id             NUMBER;
  v_sale_date           DATE;
  v_employee_first_name VARCHAR(255);
  v_employee_last_name  VARCHAR(255);
  v_employee_position   VARCHAR(255);
  v_product_name        VARCHAR(255);
  v_product_id          NUMBER;
  v_sold_quantity       NUMBER;
  v_unit_price          NUMBER;
  v_total               NUMBER;
  v_sales_found         BOOLEAN := FALSE;

  CURSOR employee_sale_cursor IS
    SELECT 
      subq.sale_id, 
      subq.sale_date,
      subq.employee_first_name,
      subq.employee_last_name,
      subq.employee_position,
      subq.product_id,
      subq.product_name,
      subq.sold_quantity, 
      subq.unit_price,
      subq.total
    FROM 
      (SELECT 
          s.sale_id,
          s."date" AS sale_date,
          e.first_name as employee_first_name,
          e.last_name as employee_last_name,
          pos.name as employee_position,
          p.product_id,
          p.name AS product_name,
          isale.sold_quantity,
          i.price AS unit_price,
          (i.price * isale.sold_quantity) AS total,
          ROW_NUMBER() OVER (ORDER BY s."date" ASC, s.sale_id ASC) AS row_num
        FROM sale s
        JOIN inventory_sale isale ON s.sale_id = isale.sale_id
        JOIN inventory i ON i.inventory_id = isale.inventory_id
        JOIN product p ON p.product_id = i.product_id
        JOIN employee e ON s.employee_id = e.employee_id
        JOIN position pos ON e.position_id = pos.position_id
        WHERE s.employee_id = p_employee_id
        ORDER BY s."date"
      ) subq
    WHERE 
      subq.row_num BETWEEN v_start_row AND v_end_row;

BEGIN
  validate_batch_numbers(v_start_row, v_end_row);
  
  IF NOT has_sales_permission(p_employee_id) THEN
    RAISE_APPLICATION_ERROR(-20012, 'This employee is not allowed to process sales.');
  END IF;

  IF NOT employee_exists(p_employee_id) THEN
    RAISE_APPLICATION_ERROR(-20013, 'This employee does not exist!');
  END IF;

  OPEN employee_sale_cursor;
  LOOP
    FETCH employee_sale_cursor INTO v_sale_id, v_sale_date, v_employee_first_name, v_employee_last_name, v_employee_position,
                           v_product_id, v_product_name, v_sold_quantity, v_unit_price, v_total;
    EXIT WHEN employee_sale_cursor%NOTFOUND;

    v_sales_found := TRUE;

    DBMS_OUTPUT.PUT_LINE(
      'Sale ID: ' || v_sale_id || ', ' || 
      'Sale date: ' || TO_CHAR(v_sale_date, 'DD-MON-YYYY') || ', ' || 
      'Employee fist name: ' || v_employee_first_name || ', ' ||
      'Employee last name: ' || v_employee_last_name || ', ' ||
      'Employee position: ' || v_employee_position || ', ' ||
      'Product ID: ' || v_product_id || ', ' ||
      'Product name: ' || v_product_name || ', ' || 
      'Sold quantity: ' || v_sold_quantity || ', ' || 
      'Unit price: ' || TO_CHAR(v_unit_price, '999.99') || ', ' || 
      'Total: ' || TO_CHAR(v_total, '9999.99')
    );

  END LOOP;
  CLOSE employee_sale_cursor;

  IF NOT v_sales_found THEN
    DBMS_OUTPUT.PUT_LINE('No sales found for employee ID: ' || p_employee_id);
  END IF;

EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error generating sale report: ' || SQLERRM);
END generate_employee_sale_report;
/

