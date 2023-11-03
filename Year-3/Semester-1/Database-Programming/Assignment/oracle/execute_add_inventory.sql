BEGIN
  add_inventory(50, 300.0, TO_DATE('10/01/2023', 'MM/DD/YYYY'), 100);
  add_inventory(200, 20.0, TO_DATE('10/02/2023', 'MM/DD/YYYY'), 110);
  add_inventory(10, 500.0, TO_DATE('10/03/2023', 'MM/DD/YYYY'), 120);
  add_inventory(100, 15.0, TO_DATE('10/04/2023', 'MM/DD/YYYY'), 130);
  add_inventory(250, 2.0, TO_DATE('10/05/2023', 'MM/DD/YYYY'), 140);
  add_inventory(60, 10.0, TO_DATE('10/06/2023', 'MM/DD/YYYY'), 150);
  add_inventory(15, 100.0, TO_DATE('10/07/2023', 'MM/DD/YYYY'), 160);
  add_inventory(70, 60.0, TO_DATE('10/08/2023', 'MM/DD/YYYY'), 170);
  add_inventory(40, 25.0, TO_DATE('10/09/2023', 'MM/DD/YYYY'), 180);
  add_inventory(30, 120.0, TO_DATE('10/10/2023', 'MM/DD/YYYY'), 190);
  add_inventory(50, 300.0, TO_DATE('10/11/2023', 'MM/DD/YYYY'), 100);
  add_inventory(30, 700.0, TO_DATE('10/12/2023', 'MM/DD/YYYY'), 200);
  add_inventory(100, 80.0, TO_DATE('10/13/2023', 'MM/DD/YYYY'), 210);
  add_inventory(150, 40.0, TO_DATE('10/14/2023', 'MM/DD/YYYY'), 220);
  add_inventory(120, 60.0, TO_DATE('10/15/2023', 'MM/DD/YYYY'), 230);
  add_inventory(20, 150.0, TO_DATE('10/16/2023', 'MM/DD/YYYY'), 240);
  add_inventory(25, 80.0, TO_DATE('10/17/2023', 'MM/DD/YYYY'), 250);
  add_inventory(90, 30.0, TO_DATE('10/18/2023', 'MM/DD/YYYY'), 260);
  add_inventory(60, 50.0, TO_DATE('10/19/2023', 'MM/DD/YYYY'), 270);
  add_inventory(200, 3.0, TO_DATE('10/20/2023', 'MM/DD/YYYY'), 280);
  add_inventory(300, 4.0, TO_DATE('10/21/2023', 'MM/DD/YYYY'), 290);
  add_inventory(30, 44.0, TO_DATE('10/15/2023', 'MM/DD/YYYY'), 220);
  add_inventory(70, 50.0, TO_DATE('10/18/2023', 'MM/DD/YYYY'), 220);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/
