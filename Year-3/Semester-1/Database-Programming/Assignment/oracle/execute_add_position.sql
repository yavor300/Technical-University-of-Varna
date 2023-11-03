BEGIN
  add_position('Manager');
  add_position('Salesperson');
  add_position('Cashier');
  add_position('Storekeeper');
  add_position('Cleaner');
  add_position('Security');
  add_position('Driver');
  add_position('Assistant Manager');
  add_position('Warehouse Worker');
  add_position('Delivery Person');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/
