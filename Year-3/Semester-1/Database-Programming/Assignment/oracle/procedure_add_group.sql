CREATE OR REPLACE PROCEDURE add_group(p_group_name IN "GROUP".name%TYPE) AS
  v_group_count INTEGER;
BEGIN
  SELECT COUNT(*)
  INTO v_group_count
  FROM "GROUP"
  WHERE UPPER(name) = UPPER(p_group_name);

  IF v_group_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20000, 'A group with the same name already exists.');
  END IF;

  INSERT INTO "GROUP" (name) VALUES (p_group_name);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_group;
/
