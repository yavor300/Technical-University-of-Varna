INSERT INTO countries VALUES(1, 'Bulgaria');

INSERT INTO cities VALUES(1, 'Varna', '9000', 1);
INSERT INTO cities VALUES(2, 'Yambol', '8600', 1);
INSERT INTO cities VALUES(3, 'Sofia', '1000', 1);
INSERT INTO cities VALUES(4, 'Plovdiv', '4000', 1);
INSERT INTO cities VALUES(5, 'Elena', '5070', 1);

INSERT INTO customers VALUES(1, 'Ivan', 'Ivanov', '+359878000000', '15A Vasil Levski Steet', 5);
INSERT INTO customers VALUES(2, 'Nikolay', 'Petrov', '+359878000001', '17B Hristo Botev Steet', 4);
INSERT INTO customers VALUES(3, 'Deyan', 'Todorov', '+359878000002', '20B Georgi B. Steet', 3);
INSERT INTO customers VALUES(4, 'Teodor', 'Borisov', '+359878000003', '13A Roza Steet', 2);
INSERT INTO customers VALUES(5, 'Petya', 'Vladislavova', '+359878000004', 'Studentska Steet', 1);

INSERT INTO car_brands VALUES(1, 'Toyota');
INSERT INTO car_brands VALUES(2, 'Mercedes-Benz');
INSERT INTO car_brands VALUES(3, 'Tesla');
INSERT INTO car_brands VALUES(4, 'Volkswagen');
INSERT INTO car_brands VALUES(5, 'BMW');

INSERT INTO car_types VALUES(1, 'SUV');
INSERT INTO car_types VALUES(2, 'Sedan');
INSERT INTO car_types VALUES(3, 'Hatchback');
INSERT INTO car_types VALUES(4, 'Van');
INSERT INTO car_types VALUES(5, 'Pickup');

INSERT INTO car_models VALUES(1, 'Highlander Hybrid', 1, 1);
INSERT INTO car_models VALUES(2, 'Avalon Hybrid', 1, 2);
INSERT INTO car_models VALUES(3, 'Corolla', 1, 3);
INSERT INTO car_models VALUES(4, 'Metris', 2, 4);
INSERT INTO car_models VALUES(5, 'X-Class', 2, 5);
INSERT INTO car_models VALUES(6, 'Model S', 3, 2);
INSERT INTO car_models VALUES(7, 'Model X', 3, 1);
INSERT INTO car_models VALUES(8, 'Model Y', 3, 3);
INSERT INTO car_models VALUES(9, 'Cybertruck', 3, 5);
INSERT INTO car_models VALUES(10, 'Ventro', 4, 2);
INSERT INTO car_models VALUES(11, '118i Hatch', 5, 3);

ALTER TABLE "CARS" MODIFY ("PRICE_PER_DAY" NUMBER(5,2));

INSERT INTO cars VALUES(11, 75.00, 11);
INSERT INTO cars VALUES(10, 90.00, 10);
INSERT INTO cars VALUES(9, 160.00, 9);
INSERT INTO cars VALUES(8, 110.00, 8);
INSERT INTO cars VALUES(7, 120.00, 7);
INSERT INTO cars VALUES(6, 85.00, 6);
INSERT INTO cars VALUES(5, 115.00, 5);
INSERT INTO cars VALUES(4, 110.00, 4);
INSERT INTO cars VALUES(3, 70.00, 3);
INSERT INTO cars VALUES(2, 100.00, 2);
INSERT INTO cars VALUES(1, 90.00, 1);

INSERT INTO job_titles VALUES(3, 'Owner');
INSERT INTO job_titles VALUES(2, 'Manager');
INSERT INTO job_titles VALUES(1, 'Salesman');

INSERT INTO employees VALUES(3, 'Jenkins', 'Hart', '+359878000007', 3);
INSERT INTO employees VALUES(2, 'Alvarez', 'Gutierrez', '+359878000006', 2);
INSERT INTO employees VALUES(1, 'Ruiz', 'Cruz', '+359878000005', 1);

INSERT INTO rentals VALUES(1, '11/01/2022', 3, 1, 1, 1);
INSERT INTO rentals VALUES(2, '11/04/2022', 2, 2, 2, 2);
INSERT INTO rentals VALUES(3, '11/06/2022', 7, 3, 3, 3);
INSERT INTO rentals VALUES(4, '11/13/2022', 1, 4, 4, 1);
INSERT INTO rentals VALUES(5, '11/14/2022', 5, 5, 5, 2);
INSERT INTO rentals VALUES(6, '11/19/2022', 6, 1, 6, 3);
INSERT INTO rentals VALUES(7, '11/25/2022', 5, 2, 7, 1);
INSERT INTO rentals VALUES(8, '11/28/2022', 3, 3, 8, 2);
INSERT INTO rentals VALUES(9, '12/01/2022', 2, 4, 9, 3);
INSERT INTO rentals VALUES(10, '12/03/2022', 5, 5, 10, 1);
INSERT INTO rentals VALUES(11, '12/08/2022', 7, 1, 11, 2);
INSERT INTO rentals VALUES(12, '12/15/2022', 5, 2, 1, 3);

UPDATE cars SET price_per_day = 150.00 WHERE car_id = 8;
UPDATE rentals SET "date" = '10/31/2022' WHERE rental_id = 1;
UPDATE employees SET phone_number = '+359878000008' WHERE employee_id = 3;
UPDATE job_titles SET title = 'CEO' WHERE title = 'Owner';
UPDATE car_models SET "model" = '128i Hatch' WHERE "model" = '118i Hatch';


SELECT brand AS "brand", "model", "type", price_per_day AS "price per day"
FROM cars 
JOIN car_models
ON cars.car_model_id = car_models.car_model_id
JOIN car_brands
ON car_models.car_brand_id = car_brands.car_brand_id
JOIN car_types
ON car_models.car_type_id = car_types.car_type_id
WHERE brand = :input OR "type" = :input OR "model" = :input OR price_per_day <= :input;

SELECT *
FROM rentals
JOIN employees
ON rentals.employee_id = employees.employee_id
JOIN cars
ON rentals.car_id = cars.car_id
JOIN car_models
ON cars.car_model_id = car_models.car_model_id
JOIN car_brands
ON car_models.car_brand_id = car_brands.car_brand_id
JOIN car_types
ON car_models.car_type_id = car_types.car_type_id
WHERE first_name = :first_name AND last_name = :last_name
ORDER BY "type" ASC, "date" ASC;

SELECT *
FROM (
    SELECT * FROM rentals
    ORDER BY rentals.rental_id DESC
)
WHERE ROWNUM <= 10
ORDER BY "days" ASC;

SELECT brand AS "brand", "model", "type", price_per_day AS "price per day", first_name AS "first name", last_name AS "last name", phone_number AS "phone_number", "date"
FROM rentals
JOIN customers
ON rentals.customer_id = customers.customer_id
JOIN cars
ON rentals.car_id = cars.car_id
JOIN car_models
ON cars.car_model_id = car_models.car_model_id
JOIN car_brands
ON car_models.car_brand_id = car_brands.car_brand_id
JOIN car_types
ON car_models.car_type_id = car_types.car_type_id
WHERE first_name = :first_name AND last_name = :last_name
ORDER BY "date" ASC;

SELECT brand AS "brand", "model", "type", price_per_day AS "price per day", first_name AS "first name", last_name AS "last name", phone_number AS "phone_number", "date"
FROM rentals
JOIN customers
ON rentals.customer_id = customers.customer_id
JOIN cars
ON rentals.car_id = cars.car_id
JOIN car_models
ON cars.car_model_id = car_models.car_model_id
JOIN car_brands
ON car_models.car_brand_id = car_brands.car_brand_id
JOIN car_types
ON car_models.car_type_id = car_types.car_type_id
WHERE "date" >= to_date(:start_period,'DD-MM-YYYY') AND "date" <= to_date(:end_period,'DD-MM-YYYY')
ORDER BY first_name ASC, last_name ASC;

