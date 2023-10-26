-- The function is designed to facilitate group name-based filtering in product queries. 
-- It accepts a group name and an optional pattern as its parameters. If the 
-- given group name matches the pattern, or if no pattern is provided, the function returns a 1, indicating 
-- a successful match. Otherwise, it returns a 0.
CREATE OR REPLACE FUNCTION filter_group_name(g_name VARCHAR2, p_group_name_pattern VARCHAR2 DEFAULT NULL) RETURN NUMBER IS
BEGIN
  IF (UPPER(g_name) LIKE '%' || UPPER(NVL(p_group_name_pattern, g_name)) || '%') THEN
    RETURN 1;
  ELSE
    RETURN 0;
  END IF;
END filter_group_name;
/
