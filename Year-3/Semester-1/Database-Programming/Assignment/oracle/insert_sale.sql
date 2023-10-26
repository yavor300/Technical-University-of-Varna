-- Populate the sale table.
BEGIN
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/1/2023', 100, 110);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/2/2023', 110, 110);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/3/2023', 120, 170);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/4/2023', 100, 170);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/5/2023', 140, 250);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/6/2023', 150, 250);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/7/2023', 160, 250);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/8/2023', 100, 170);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/9/2023', 180, 110);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/23/2023', 200, 110);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/10/2023', 100, 110);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/11/2023', 210, 110);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/12/2023', 120, 170);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/13/2023', 230, 170);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/14/2023', 100, 250);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/15/2023', 250, 250);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/16/2023', 260, 250);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/17/2023', 270, 170);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/18/2023', 280, 110);
  INSERT INTO sale ("date", customer_id, employee_id) VALUES ('10/19/2023', 290, 110);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
