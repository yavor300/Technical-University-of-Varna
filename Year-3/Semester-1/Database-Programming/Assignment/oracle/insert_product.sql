-- Populate the product table.
BEGIN
  INSERT INTO product (name, group_id) VALUES ('Smartphone', 100);
  INSERT INTO product (name, group_id) VALUES ('T-Shirt', 110);
  INSERT INTO product (name, group_id) VALUES ('Sofa', 120);
  INSERT INTO product (name, group_id) VALUES ('Action Figure', 130);
  INSERT INTO product (name, group_id) VALUES ('Pasta', 140);
  INSERT INTO product (name, group_id) VALUES ('Novel', 150);
  INSERT INTO product (name, group_id) VALUES ('Guitar', 160);
  INSERT INTO product (name, group_id) VALUES ('PS5 Game', 170);
  INSERT INTO product (name, group_id) VALUES ('Football', 180);
  INSERT INTO product (name, group_id) VALUES ('Tent', 190);
  INSERT INTO product (name, group_id) VALUES ('Laptop', 100);
  INSERT INTO product (name, group_id) VALUES ('Bluetooth Headphones', 100);
  INSERT INTO product (name, group_id) VALUES ('Jeans', 110);
  INSERT INTO product (name, group_id) VALUES ('Sneakers', 110);
  INSERT INTO product (name, group_id) VALUES ('Coffee Table', 120);
  INSERT INTO product (name, group_id) VALUES ('Bookshelf', 120);
  INSERT INTO product (name, group_id) VALUES ('Board Game', 130);
  INSERT INTO product (name, group_id) VALUES ('Remote Control Car', 130);
  INSERT INTO product (name, group_id) VALUES ('Organic Milk', 140);
  INSERT INTO product (name, group_id) VALUES ('Eggs', 140);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
