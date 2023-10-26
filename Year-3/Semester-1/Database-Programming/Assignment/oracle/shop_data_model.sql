-- Populate the customer table
INSERT INTO customer VALUES (1, 'John', 'Smith', '555-555-5551');
INSERT INTO customer VALUES (2, 'Jane', 'Doe', '555-555-5552');
INSERT INTO customer VALUES (3, 'Robert', 'Johnson', '555-555-5553');
INSERT INTO customer VALUES (4, 'Mary', 'Davis', '555-555-5554');
INSERT INTO customer VALUES (5, 'David', 'Lee', '555-555-5555');
INSERT INTO customer VALUES (6, 'Peter', 'Parker', '555-555-5556');
INSERT INTO customer VALUES (7, 'Clark', 'Kent', '555-555-5557');
INSERT INTO customer VALUES (8, 'Bruce', 'Wayne', '555-555-5558');
INSERT INTO customer VALUES (9, 'Tony', 'Stark', '555-555-5559');
INSERT INTO customer VALUES (10, 'Steve', 'Rogers', '555-555-5560');
INSERT INTO customer VALUES (11, 'Diana', 'Prince', '555-555-5561');
INSERT INTO customer VALUES (12, 'Barry', 'Allen', '555-555-5562');
INSERT INTO customer VALUES (13, 'Arthur', 'Curry', '555-555-5563');
INSERT INTO customer VALUES (14, 'Hal', 'Jordan', '555-555-5564');
INSERT INTO customer VALUES (15, 'Oliver', 'Queen', '555-555-5565');
INSERT INTO customer VALUES (16, 'Selina', 'Kyle', '555-555-5566');
INSERT INTO customer VALUES (17, 'Natasha', 'Romanoff', '555-555-5567');
INSERT INTO customer VALUES (18, 'Bruce', 'Banner', '555-555-5568');
INSERT INTO customer VALUES (19, 'Wanda', 'Maximoff', '555-555-5569');
INSERT INTO customer VALUES (20, 'Scott', 'Lang', '555-555-5570');

-- Populate the "group" table
INSERT INTO "group" VALUES (1, 'Electronics');
INSERT INTO "group" VALUES (2, 'Apparel');
INSERT INTO "group" VALUES (3, 'Furniture');
INSERT INTO "group" VALUES (4, 'Toys');
INSERT INTO "group" VALUES (5, 'Grocery');
INSERT INTO "group" VALUES (6, 'Books');
INSERT INTO "group" VALUES (7, 'Music');
INSERT INTO "group" VALUES (8, 'Video Games');
INSERT INTO "group" VALUES (9, 'Sports');
INSERT INTO "group" VALUES (10, 'Outdoors');

-- Populate the product table
INSERT INTO product VALUES (1, 'Smartphone', 1);
INSERT INTO product VALUES (2, 'T-Shirt', 2);
INSERT INTO product VALUES (3, 'Sofa', 3);
INSERT INTO product VALUES (4, 'Action Figure', 4);
INSERT INTO product VALUES (5, 'Pasta', 5);
INSERT INTO product VALUES (6, 'Novel', 6);
INSERT INTO product VALUES (7, 'Guitar', 7);
INSERT INTO product VALUES (8, 'PS5 Game', 8);
INSERT INTO product VALUES (9, 'Football', 9);
INSERT INTO product VALUES (10, 'Tent', 10);
INSERT INTO product VALUES (11, 'Laptop', 1);
INSERT INTO product VALUES (12, 'Bluetooth Headphones', 1);
INSERT INTO product VALUES (13, 'Jeans', 2);
INSERT INTO product VALUES (14, 'Sneakers', 2);
INSERT INTO product VALUES (15, 'Coffee Table', 3);
INSERT INTO product VALUES (16, 'Bookshelf', 3);
INSERT INTO product VALUES (17, 'Board Game', 4);
INSERT INTO product VALUES (18, 'Remote Control Car', 4);
INSERT INTO product VALUES (19, 'Organic Milk', 5);
INSERT INTO product VALUES (20, 'Eggs', 5);

-- Populate the position table
INSERT INTO position VALUES (1, 'Manager');
INSERT INTO position VALUES (2, 'Salesperson');
INSERT INTO position VALUES (3, 'Cashier');
INSERT INTO position VALUES (4, 'Storekeeper');
INSERT INTO position VALUES (5, 'Cleaner');
INSERT INTO position VALUES (6, 'Security');
INSERT INTO position VALUES (7, 'Driver');
INSERT INTO position VALUES (8, 'Assistant Manager');
INSERT INTO position VALUES (9, 'Warehouse Worker');
INSERT INTO position VALUES (10, 'Delivery Person');

-- Populate the employee table
INSERT INTO employee VALUES (1, 'Alice Green', '555-556-5551', 1);
INSERT INTO employee VALUES (3, 'Charlie Black', '555-556-5553', 3);
INSERT INTO employee VALUES (4, 'Debbie White', '555-556-5554', 4);
INSERT INTO employee VALUES (5, 'Eve Gray', '555-556-5555', 5);
INSERT INTO employee VALUES (7, 'Grace Teal', '555-556-5557', 7);
INSERT INTO employee VALUES (9, 'Ivy Blue', '555-556-5559', 9);
INSERT INTO employee VALUES (10, 'Jack Red', '555-556-5560', 10);
INSERT INTO employee VALUES (11, 'Liam Stone', '555-556-5561', 8);
INSERT INTO employee VALUES (15, 'Ava Sun', '555-556-5565', 6);
INSERT INTO employee VALUES (16, 'Sophia Rain', '555-556-5566', 5);
INSERT INTO employee VALUES (17, 'Lucas River', '555-556-5567', 4);
INSERT INTO employee VALUES (18, 'Mia Ocean', '555-556-5568', 3);
INSERT INTO employee VALUES (19, 'Ethan Forest', '555-556-5569', 7);
INSERT INTO employee VALUES (20, 'Isabella Mountain', '555-556-5570', 9);
-- Salesperson
INSERT INTO employee VALUES (2, 'Bob Brown', '555-556-5552', 2);
INSERT INTO employee VALUES (6, 'Frank Purple', '555-556-5556', 2);
INSERT INTO employee VALUES (8, 'Hank Yellow', '555-556-5558', 2);
INSERT INTO employee VALUES (12, 'Olivia Wood', '555-556-5562', 2);
INSERT INTO employee VALUES (13, 'Noah Sand', '555-556-5563', 2);
INSERT INTO employee VALUES (14, 'Emma Grass', '555-556-5564', 2);

-- Populate the inventory table
INSERT INTO inventory VALUES (1, 50, 300.0, '10/01/2023', 1);
INSERT INTO inventory VALUES (2, 200, 20.0, '10/02/2023', 2);
INSERT INTO inventory VALUES (3, 10, 500.0, '10/03/2023', 3);
INSERT INTO inventory VALUES (4, 100, 15.0, '10/04/2023', 4);
INSERT INTO inventory VALUES (5, 250, 2.0, '10/05/2023', 5);
INSERT INTO inventory VALUES (6, 60, 10.0, '10/06/2023', 6);
INSERT INTO inventory VALUES (7, 15, 100.0, '10/07/2023', 7);
INSERT INTO inventory VALUES (8, 70, 60.0, '10/08/2023', 8);
INSERT INTO inventory VALUES (9, 40, 25.0, '10/09/2023', 9);
INSERT INTO inventory VALUES (10, 30, 120.0, '10/10/2023', 10);
INSERT INTO inventory VALUES (11, 50, 300.0, '10/11/2023', 1);
INSERT INTO inventory VALUES (12, 30, 700.0, '10/12/2023', 11);
INSERT INTO inventory VALUES (13, 100, 80.0, '10/13/2023', 12);
INSERT INTO inventory VALUES (14, 150, 40.0, '10/14/2023', 13);
INSERT INTO inventory VALUES (15, 120, 60.0, '10/15/2023', 14);
INSERT INTO inventory VALUES (16, 20, 150.0, '10/16/2023', 15);
INSERT INTO inventory VALUES (17, 25, 80.0, '10/17/2023', 16);
INSERT INTO inventory VALUES (18, 90, 30.0, '10/18/2023', 17);
INSERT INTO inventory VALUES (19, 60, 50.0, '10/19/2023', 18);
INSERT INTO inventory VALUES (20, 200, 3.0, '10/20/2023', 19);
INSERT INTO inventory VALUES (21, 300, 4.0, '10/21/2023', 20);
INSERT INTO inventory VALUES (22, 30, 44.0, '10/15/2023', 13);
INSERT INTO inventory VALUES (23, 70, 50.0, '10/18/2023', 13);

-- Populate the sale table
INSERT INTO sale VALUES (1, '10/1/2023', 1, 2);
INSERT INTO sale VALUES (2, '10/2/2023', 2, 2);
INSERT INTO sale VALUES (3, '10/3/2023', 3, 8);
INSERT INTO sale VALUES (4, '10/4/2023', 1, 8);
INSERT INTO sale VALUES (5, '10/5/2023', 5, 6);
INSERT INTO sale VALUES (6, '10/6/2023', 6, 6);
INSERT INTO sale VALUES (7, '10/7/2023', 7, 6);
INSERT INTO sale VALUES (8, '10/8/2023', 1, 8);
INSERT INTO sale VALUES (9, '10/9/2023', 9, 2);
INSERT INTO sale VALUES (10, '10/23/2023', 10, 2);
INSERT INTO sale VALUES (11, '10/10/2023', 1, 2);
INSERT INTO sale VALUES (12, '10/11/2023', 12, 2);
INSERT INTO sale VALUES (13, '10/12/2023', 3, 8);
INSERT INTO sale VALUES (14, '10/13/2023', 14, 8);
INSERT INTO sale VALUES (15, '10/14/2023', 1, 6);
INSERT INTO sale VALUES (16, '10/15/2023', 16, 6);
INSERT INTO sale VALUES (17, '10/16/2023', 17, 6);
INSERT INTO sale VALUES (18, '10/17/2023', 18, 8);
INSERT INTO sale VALUES (19, '10/18/2023', 19, 2);
INSERT INTO sale VALUES (20, '10/19/2023', 20, 2);


-- Populate the inventory_sale table
INSERT INTO inventory_sale VALUES (1, 1, 1, 1);
INSERT INTO inventory_sale VALUES (2, 2, 2, 2);
INSERT INTO inventory_sale VALUES (3, 1, 3, 3);
INSERT INTO inventory_sale VALUES (4, 3, 4, 4);
INSERT INTO inventory_sale VALUES (5, 5, 5, 5);
INSERT INTO inventory_sale VALUES (6, 4, 6, 6);
INSERT INTO inventory_sale VALUES (7, 2, 7, 7);
INSERT INTO inventory_sale VALUES (8, 6, 8, 8);
INSERT INTO inventory_sale VALUES (9, 3, 9, 9);
INSERT INTO inventory_sale VALUES (10, 7, 10, 10);
INSERT INTO inventory_sale VALUES (11, 11, 4, 11);
INSERT INTO inventory_sale VALUES (12, 12, 5, 12);
INSERT INTO inventory_sale VALUES (13, 13, 11, 13);
INSERT INTO inventory_sale VALUES (14, 14, 12, 14);
INSERT INTO inventory_sale VALUES (15, 15, 13, 15);
INSERT INTO inventory_sale VALUES (16, 16, 14, 16);
INSERT INTO inventory_sale VALUES (17, 17, 15, 17);
INSERT INTO inventory_sale VALUES (18, 18, 16, 18);
INSERT INTO inventory_sale VALUES (19, 19, 17, 19);
INSERT INTO inventory_sale VALUES (20, 20, 18, 20);

-- Updates

UPDATE customer
SET name = 'John Doe', phone_number = '555-123-4567'
WHERE customer_id = 1;

UPDATE employee
SET name = 'Alice Smith', phone_number = '555-987-6543'
WHERE employee_id = 5;

UPDATE product
SET name = 'Wireless Mouse',
    group_id = (SELECT group_id FROM "group" WHERE name = 'Electronics')
WHERE product_id = 3;

UPDATE inventory
SET quantity = 50, price = 150.50
WHERE inventory_id = 1;

UPDATE inventory_sale
SET sold_quantity = 20
WHERE inventory_sale_id = 1;

UPDATE sale
SET "date" = '01-JAN-23'
WHERE sale_id = 1;

UPDATE position
SET name = 'Marketing Expert'
WHERE position_id = 6;

-- Retrieves product details with their associated group names and prices.
-- Only products with a price greater than or equal to a user-provided parameter are retrieved.
SELECT 
    p.product_id, 
    p.name AS product_name, 
    g.name AS group_name, 
    i.price,
    i.quantity AS available_quantity,
    i.import_date
FROM 
    product p
JOIN 
    inventory i ON p.product_id = i.product_id
JOIN 
    "GROUP" g ON p.group_id = g.group_id
WHERE 
    i.price >= :param
ORDER BY 
    p.product_id ASC,
    i.import_date ASC;

-- Retrieves product details with their associated group names and prices.
-- Only products with a price less than or equal to a user-provided parameter are retrieved.
SELECT 
    p.product_id, 
    p.name AS product_name, 
    g.name AS group_name, 
    i.price
FROM 
    product p
JOIN 
    inventory i ON p.product_id = i.product_id
JOIN 
    "group" g ON p.group_id = g.group_id
WHERE 
    i.price <= :param;

-- Retrieves product details with their associated group names based on a user-provided product name search pattern.
SELECT 
    p.product_id,
    p.name AS product_name,
    g.name AS group_name,
    i.price
FROM 
    product p
JOIN 
    inventory i ON p.product_id = i.product_id
JOIN 
    "group" g ON p.group_id = g.group_id
WHERE 
    p.name LIKE :product_name_pattern;

-- Retrieves product details with their associated group names based on a user-provided group name search pattern.
SELECT 
    p.product_id,
    p.name AS product_name,
    g.name AS group_name,
    i.price
FROM 
    product p
JOIN 
    inventory i ON p.product_id = i.product_id
JOIN 
    "group" g ON p.group_id = g.group_id
WHERE 
    g.name LIKE :group_name_pattern;

SELECT 
    p.product_id, 
    p.name AS product_name, 
    g.name AS group_name, 
    i.price
FROM 
    product p
JOIN 
    inventory i ON p.product_id = i.product_id
JOIN 
    "group" g ON p.group_id = g.group_id
WHERE 
    i.price >= :price_param AND p.name LIKE :product_name_pattern;

SELECT 
    p.product_id, 
    p.name AS product_name, 
    g.name AS group_name, 
    i.price
FROM 
    product p
JOIN 
    inventory i ON p.product_id = i.product_id
JOIN 
    "group" g ON p.group_id = g.group_id
WHERE 
    i.price <= :price_param AND p.name LIKE :product_name_pattern;

DROP TABLE inventory_sale;
DROP TABLE sale;
DROP TABLE employee;
DROP TABLE customer;
DROP TABLE position;
DROP TABLE inventory;
DROP TABLE product;
DROP TABLE "GROUP";

-- тригер за update-ане на id
-- проверка за наличност
-- само търговци могат да затвярят сделки
