-- Validates the given date parameters ensuring they are not in the future,
-- and that the start date is not greater than the end date.
CREATE OR REPLACE PROCEDURE validate_dates(
  p_start_date IN DATE,
  p_end_date   IN DATE
) AS
BEGIN
  IF p_start_date > SYSDATE OR p_end_date > SYSDATE THEN
    RAISE_APPLICATION_ERROR(-20001, 'Provided dates must not be in the future.');
  END IF;

  IF p_start_date > p_end_date THEN
    RAISE_APPLICATION_ERROR(-20002, 'Start date must not be greater than end date.');
  END IF;

EXCEPTION
  WHEN OTHERS THEN
    RAISE;
END validate_dates;
/
