-- Populate the sale table.
BEGIN
  add_sale(TO_DATE('10/1/2023', 'MM/DD/YYYY'), 100, 110);
  add_sale(TO_DATE('10/2/2023', 'MM/DD/YYYY'), 110, 110);
  add_sale(TO_DATE('10/3/2023', 'MM/DD/YYYY'), 120, 170);
  add_sale(TO_DATE('10/4/2023', 'MM/DD/YYYY'), 100, 170);
  add_sale(TO_DATE('10/5/2023', 'MM/DD/YYYY'), 140, 250);
  add_sale(TO_DATE('10/6/2023', 'MM/DD/YYYY'), 150, 250);
  add_sale(TO_DATE('10/7/2023', 'MM/DD/YYYY'), 160, 250);
  add_sale(TO_DATE('10/8/2023', 'MM/DD/YYYY'), 100, 170);
  add_sale(TO_DATE('10/9/2023', 'MM/DD/YYYY'), 180, 110);
  add_sale(TO_DATE('10/23/2023', 'MM/DD/YYYY'), 200, 110);
  add_sale(TO_DATE('10/10/2023', 'MM/DD/YYYY'), 100, 110);
  add_sale(TO_DATE('10/11/2023', 'MM/DD/YYYY'), 210, 110);
  add_sale(TO_DATE('10/12/2023', 'MM/DD/YYYY'), 120, 170);
  add_sale(TO_DATE('10/13/2023', 'MM/DD/YYYY'), 230, 170);
  add_sale(TO_DATE('10/14/2023', 'MM/DD/YYYY'), 100, 250);
  add_sale(TO_DATE('10/15/2023', 'MM/DD/YYYY'), 250, 250);
  add_sale(TO_DATE('10/16/2023', 'MM/DD/YYYY'), 260, 250);
  add_sale(TO_DATE('10/17/2023', 'MM/DD/YYYY'), 270, 170);
  add_sale(TO_DATE('10/18/2023', 'MM/DD/YYYY'), 280, 110);
  add_sale(TO_DATE('10/19/2023', 'MM/DD/YYYY'), 290, 110);
END;
/
