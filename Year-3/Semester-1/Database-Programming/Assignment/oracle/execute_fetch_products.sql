-- This PL/SQL block invokes the fetch_products procedure, which retrieves a list of products 
-- based on specified filtering criteria: product name pattern, group name pattern, and price range. 
-- The results are also limited to a batch range, defined by the batch start and end parameters.
BEGIN
  fetch_products(:batch_start, :batch_end, :price_start_interval, :price_end_interval, :product_name_pattern, :group_name_pattern);
END;
/
