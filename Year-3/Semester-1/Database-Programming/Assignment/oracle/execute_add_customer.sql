BEGIN
  add_customer('John', 'Smith', '555-555-5551');
  add_customer('Jane', 'Doe', '555-555-5552');
  add_customer('Robert', 'Johnson', '555-555-5553');
  add_customer('Mary', 'Davis', '555-555-5554');
  add_customer('David', 'Lee', '555-555-5555');
  add_customer('Peter', 'Parker', '555-555-5556');
  add_customer('Clark', 'Kent', '555-555-5557');
  add_customer('Bruce', 'Wayne', '555-555-5558');
  add_customer('Tony', 'Stark', '555-555-5559');
  add_customer('Steve', 'Rogers', '555-555-5560');
  add_customer('Diana', 'Prince', '555-555-5561');
  add_customer('Barry', 'Allen', '555-555-5562');
  add_customer('Arthur', 'Curry', '555-555-5563');
  add_customer('Hal', 'Jordan', '555-555-5564');
  add_customer('Oliver', 'Queen', '555-555-5565');
  add_customer('Selina', 'Kyle', '555-555-5566');
  add_customer('Natasha', 'Romanoff', '555-555-5567');
  add_customer('Bruce', 'Banner', '555-555-5568');
  add_customer('Wanda', 'Maximoff', '555-555-5569');
  add_customer('Scott', 'Lang', '555-555-5570');
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/