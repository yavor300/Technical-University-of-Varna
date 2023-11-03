CREATE OR REPLACE PROCEDURE add_position(p_name IN position.name%type) IS
  v_count NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO v_count
  FROM position
  WHERE UPPER(name) = UPPER(p_name);

  IF v_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Position name must be unique. The provided name already exists.');
  END IF;

  INSERT INTO position (name) VALUES (p_name);

  DBMS_OUTPUT.PUT_LINE('New position "' || p_name || '" has been added successfully.');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END add_position;
/
