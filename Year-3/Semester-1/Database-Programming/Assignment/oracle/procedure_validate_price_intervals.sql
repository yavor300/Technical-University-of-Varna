-- This procedure validates the given price intervals. It checks for two main conditions:
-- 1. Ensures that neither the start nor the end price intervals are negative.
-- 2. Ensures that the start price interval is not greater than the end price interval.
-- If any of these conditions are violated, the procedure raises a custom application error with a descriptive message.
CREATE OR REPLACE PROCEDURE validate_price_intervals(
  p_price_start_interval IN NUMBER,
  p_price_end_interval IN NUMBER
) AS
BEGIN
  IF (p_price_start_interval IS NOT NULL AND p_price_start_interval < 0) OR 
    (p_price_end_interval IS NOT NULL AND p_price_end_interval < 0) THEN
    RAISE_APPLICATION_ERROR(-20001, 'Price intervals cannot be negative.');
  ELSIF p_price_start_interval > p_price_end_interval THEN
    RAISE_APPLICATION_ERROR(-20002, 'Start interval cannot be greater than end interval.');
  END IF;
END validate_price_intervals;
/
