-- Populate the "inventory_sale" table
BEGIN
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (1, 100, 100);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (2, 110, 110);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (3, 100, 120);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (4, 120, 130);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (5, 140, 140);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (6, 130, 150);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (7, 110, 160);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (8, 150, 170);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (9, 120, 180);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (10, 160, 190);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (11, 200, 130);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (12, 210, 140);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (13, 220, 200);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (14, 230, 210);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (15, 240, 220);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (16, 250, 230);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (17, 260, 240);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (18, 270, 250);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (19, 280, 260);
  INSERT INTO inventory_sale (sold_quantity, inventory_id, sale_id) VALUES (20, 290, 270);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
