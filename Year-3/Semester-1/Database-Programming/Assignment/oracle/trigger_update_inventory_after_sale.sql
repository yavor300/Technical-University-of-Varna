-- This trigger is invoked after a new record
-- is inserted into the inventory_sale table. For every new sale entry, it automatically
-- updates the quantity in the inventory table, deducting the sold quantity from the existing inventory.
-- The deduction is based on matching the inventory_id from
-- the newly inserted inventory_sale record to the corresponding inventory_id in the inventory table.
CREATE OR REPLACE TRIGGER tr_update_inventory_after_sale
AFTER INSERT ON inventory_sale
FOR EACH ROW
BEGIN
  UPDATE inventory
  SET quantity = quantity - :NEW.sold_quantity
  WHERE inventory_id = :NEW.inventory_id;
END tr_update_inventory_after_sale;
/
