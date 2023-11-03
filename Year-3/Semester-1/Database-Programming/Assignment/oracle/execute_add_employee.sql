BEGIN
  add_employee('Alice', 'Green', '555-556-5551', 100);
  add_employee('Charlie', 'Black', '555-556-5553', 120);
  add_employee('Debbie', 'White', '555-556-5554', 130);
  add_employee('Eve', 'Gray', '555-556-5555', 140);
  add_employee('Grace', 'Teal', '555-556-5557', 160);
  add_employee('Ivy', 'Blue', '555-556-5559', 180);
  add_employee('Jack', 'Red', '555-556-5560', 190);
  add_employee('Liam', 'Stone', '555-556-5561', 170);
  add_employee('Ava', 'Sun', '555-556-5565', 150);
  add_employee('Sophia', 'Rain', '555-556-5566', 140);
  add_employee('Lucas', 'River', '555-556-5567', 130);
  add_employee('Mia', 'Ocean', '555-556-5568', 120);
  add_employee('Ethan', 'Forest', '555-556-5569', 160);
  add_employee('Isabella', 'Mountain', '555-556-5570', 180);
  add_employee('Bob', 'Brown', '555-556-5552', 110);
  add_employee('Frank', 'Purple', '555-556-5556', 110);
  add_employee('Hank', 'Yellow', '555-556-5558', 110);
  add_employee('Olivia', 'Wood', '555-556-5562', 110);
  add_employee('Noah', 'Sand', '555-556-5563', 110);
  add_employee('Emma', 'Grass', '555-556-5564', 110);
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/
