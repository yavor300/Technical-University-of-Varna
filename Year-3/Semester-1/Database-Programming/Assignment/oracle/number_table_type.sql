-- This PL/SQL block defines a custom type named `NUMBER_TABLE_TYPE`. 
-- The type represents a table of numbers, allowing users to store and manipulate 
-- sets of numeric values as a single entity within PL/SQL blocks, procedures, and functions.
-- In our specific use it to pass arrays of product IDs 
-- and quantities to the `buy_products` procedure. This design simplifies the procedure call 
-- by allowing us to input multiple product IDs and quantities in a single argument, 
facilitating batch operations like bulk purchasing.
*/
BEGIN
  CREATE OR REPLACE TYPE NUMBER_TABLE_TYPE IS TABLE OF NUMBER;
END;
/
