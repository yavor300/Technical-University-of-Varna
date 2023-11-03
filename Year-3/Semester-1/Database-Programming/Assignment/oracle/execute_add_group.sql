BEGIN
  add_group('Electronics');
  add_group('Apparel');
  add_group('Furniture');
  add_group('Toys');
  add_group('Grocery');
  add_group('Books');
  add_group('Music');
  add_group('Video Games');
  add_group('Sports');
  add_group('Outdoors');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/
