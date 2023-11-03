-- Populate the "inventory_sale" table
BEGIN
  add_inventory_sale(1, 100, 100);
  add_inventory_sale(2, 110, 110);
  add_inventory_sale(3, 100, 120);
  add_inventory_sale(4, 120, 130);
  add_inventory_sale(5, 140, 140);
  add_inventory_sale(6, 130, 150);
  add_inventory_sale(7, 110, 160);
  add_inventory_sale(8, 150, 170);
  add_inventory_sale(9, 120, 180);
  add_inventory_sale(10, 160, 190);
  add_inventory_sale(11, 200, 130);
  add_inventory_sale(12, 210, 140);
  add_inventory_sale(13, 220, 200);
  add_inventory_sale(14, 230, 210);
  add_inventory_sale(15, 240, 220);
  add_inventory_sale(16, 250, 230);
  add_inventory_sale(17, 260, 240);
  add_inventory_sale(18, 270, 250);
  add_inventory_sale(19, 280, 260);
  add_inventory_sale(20, 290, 270);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
