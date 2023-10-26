-- The procedure is designed to ensure the validity of batch boundaries
-- provided to product-fetching functions and procedures. It checks if the batch start and
-- end values are positive and confirms that the start value doesn't surpass the end value.
-- If any of these conditions aren't met, it raises an application-specific error.
CREATE OR REPLACE PROCEDURE validate_batch_numbers(
  p_batch_start IN NUMBER,
  p_batch_end IN NUMBER
) AS
BEGIN
  IF (p_batch_start IS NOT NULL AND p_batch_start <= 0) OR 
    (p_batch_end IS NOT NULL AND p_batch_end <= 0) THEN
    RAISE_APPLICATION_ERROR(-20003, 'Batch start and end values should be positive.');
  ELSIF p_batch_start > p_batch_end THEN
    RAISE_APPLICATION_ERROR(-20004, 'Batch start cannot be greater than batch end.');
  END IF;
END validate_batch_numbers;
/
