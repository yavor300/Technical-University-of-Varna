-- Populate the GROUP table.
BEGIN
  INSERT INTO "GROUP" (name) VALUES ('Electronics');
  INSERT INTO "GROUP" (name) VALUES ('Apparel');
  INSERT INTO "GROUP" (name) VALUES ('Furniture');
  INSERT INTO "GROUP" (name) VALUES ('Toys');
  INSERT INTO "GROUP" (name) VALUES ('Grocery');
  INSERT INTO "GROUP" (name) VALUES ('Books');
  INSERT INTO "GROUP" (name) VALUES ('Music');
  INSERT INTO "GROUP" (name) VALUES ('Video Games');
  INSERT INTO "GROUP" (name) VALUES ('Sports');
  INSERT INTO "GROUP" (name) VALUES ('Outdoors');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
/
