-- Populate the position table.
BEGIN
  INSERT INTO position (name) VALUES ('Manager');
  INSERT INTO position (name) VALUES ('Salesperson');
  INSERT INTO position (name) VALUES ('Cashier');
  INSERT INTO position (name) VALUES ('Storekeeper');
  INSERT INTO position (name) VALUES ('Cleaner');
  INSERT INTO position (name) VALUES ('Security');
  INSERT INTO position (name) VALUES ('Driver');
  INSERT INTO position (name) VALUES ('Assistant Manager');
  INSERT INTO position (name) VALUES ('Warehouse Worker');
  INSERT INTO position (name) VALUES ('Delivery Person');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
