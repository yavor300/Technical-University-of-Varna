-- This index optimizes searches based on the product name.
CREATE INDEX idx_product_name ON product(name);

-- This index optimizes operations on the GROUP table's name column.
CREATE INDEX idx_group_name ON "GROUP"(name);

-- This index facilitates quicker searches, sorts on the price in the inventory table.
CREATE INDEX idx_inventory_price ON inventory(price);

-- This index aids in operations that filter, sort sales based on the date.
CREATE INDEX idx_sale_date ON sale("date");
