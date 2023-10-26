-- Populate the "inventory" table
BEGIN
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (50, 300.0, '10/01/2023', 100);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (200, 20.0, '10/02/2023', 110);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (10, 500.0, '10/03/2023', 120);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (100, 15.0, '10/04/2023', 130);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (250, 2.0, '10/05/2023', 140);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (60, 10.0, '10/06/2023', 150);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (15, 100.0, '10/07/2023', 160);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (70, 60.0, '10/08/2023', 170);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (40, 25.0, '10/09/2023', 180);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (30, 120.0, '10/10/2023', 190);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (50, 300.0, '10/11/2023', 100);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (30, 700.0, '10/12/2023', 200);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (100, 80.0, '10/13/2023', 210);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (150, 40.0, '10/14/2023', 220);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (120, 60.0, '10/15/2023', 230);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (20, 150.0, '10/16/2023', 240);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (25, 80.0, '10/17/2023', 250);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (90, 30.0, '10/18/2023', 260);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (60, 50.0, '10/19/2023', 270);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (200, 3.0, '10/20/2023', 280);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (300, 4.0, '10/21/2023', 290);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (30, 44.0, '10/15/2023', 220);
  INSERT INTO inventory (quantity, price, import_date, product_id) VALUES (70, 50.0, '10/18/2023', 220);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
