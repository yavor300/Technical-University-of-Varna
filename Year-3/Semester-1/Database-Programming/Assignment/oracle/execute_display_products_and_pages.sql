-- This procedure fetches and displays products based on provided parameters and then 
-- calculates the total number of pages based on batch start and end numbers.
BEGIN
  display_products_and_pages(:batch_start, :batch_end, :price_start_interval, :price_end_interval, :product_name_pattern, :group_name_pattern);
END;
/
