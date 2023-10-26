-- The purpose of this block of code is to invoke the get_total_pages procedure,
-- which calculates the total number of pages based on the specified parameters.
-- This procedure is useful when implementing pagination in applications where
-- the results need to be split across multiple pages.
BEGIN
  get_total_pages(:batch_start, :batch_end, :price_start_interval, :price_end_interval, :product_name_pattern, :group_name_pattern);
END;
/
