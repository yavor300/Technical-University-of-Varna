-- Populate the customer table.
BEGIN
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('John', 'Smith', '555-555-5551');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Jane', 'Doe', '555-555-5552');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Robert', 'Johnson', '555-555-5553');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Mary', 'Davis', '555-555-5554');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('David', 'Lee', '555-555-5555');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Peter', 'Parker', '555-555-5556');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Clark', 'Kent', '555-555-5557');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Bruce', 'Wayne', '555-555-5558');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Tony', 'Stark', '555-555-5559');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Steve', 'Rogers', '555-555-5560');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Diana', 'Prince', '555-555-5561');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Barry', 'Allen', '555-555-5562');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Arthur', 'Curry', '555-555-5563');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Hal', 'Jordan', '555-555-5564');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Oliver', 'Queen', '555-555-5565');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Selina', 'Kyle', '555-555-5566');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Natasha', 'Romanoff', '555-555-5567');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Bruce', 'Banner', '555-555-5568');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Wanda', 'Maximoff', '555-555-5569');
  INSERT INTO customer (first_name, last_name, phone_number) VALUES ('Scott', 'Lang', '555-555-5570');
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
