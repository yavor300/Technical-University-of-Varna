-- This anonymous PL/SQL block is designed to execute the generate_customer_sale_report procedure
-- for a specific customer using the provided customer ID and optional batch start and end values
-- for pagination.
BEGIN
  generate_customer_sale_report(:customer_phone_number, :batch_start, :batch_end);
END;
/
