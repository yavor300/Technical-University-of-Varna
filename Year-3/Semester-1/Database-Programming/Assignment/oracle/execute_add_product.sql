BEGIN
  add_product('Smartphone', 100);
  add_product('T-Shirt', 110);
  add_product('Sofa', 120);
  add_product('Action Figure', 130);
  add_product('Pasta', 140);
  add_product('Novel', 150);
  add_product('Guitar', 160);
  add_product('PS5 Game', 170);
  add_product('Football', 180);
  add_product('Tent', 190);
  add_product('Laptop', 100);
  add_product('Bluetooth Headphones', 100);
  add_product('Jeans', 110);
  add_product('Sneakers', 110);
  add_product('Coffee Table', 120);
  add_product('Bookshelf', 120);
  add_product('Board Game', 130);
  add_product('Remote Control Car', 130);
  add_product('Organic Milk', 140);
  add_product('Eggs', 140);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/
