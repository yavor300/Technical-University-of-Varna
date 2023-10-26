-- Populate the employee table.
BEGIN
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Alice', 'Green', '555-556-5551', 100);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Charlie', 'Black', '555-556-5553', 120);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Debbie', 'White', '555-556-5554', 130);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Eve', 'Gray', '555-556-5555', 140);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Grace', 'Teal', '555-556-5557', 160);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Ivy', 'Blue', '555-556-5559', 180);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Jack', 'Red', '555-556-5560', 190);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Liam', 'Stone', '555-556-5561', 170);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Ava', 'Sun', '555-556-5565', 150);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Sophia', 'Rain', '555-556-5566', 140);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Lucas', 'River', '555-556-5567', 130);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Mia', 'Ocean', '555-556-5568', 120);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Ethan', 'Forest', '555-556-5569', 160);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Isabella', 'Mountain', '555-556-5570', 180);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Bob', 'Brown', '555-556-5552', 110);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Frank', 'Purple', '555-556-5556', 110);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Hank', 'Yellow', '555-556-5558', 110);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Olivia', 'Wood', '555-556-5562', 110);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Noah', 'Sand', '555-556-5563', 110);
  INSERT INTO employee (first_name, last_name, phone_number, position_id) VALUES ('Emma', 'Grass', '555-556-5564', 110);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
