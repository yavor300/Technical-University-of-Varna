CREATE TABLE car_brands (
    "id"  INTEGER NOT NULL,
    brand VARCHAR2(255) NOT NULL
);

ALTER TABLE car_brands ADD CONSTRAINT car_brands_pk PRIMARY KEY ( "id" );

ALTER TABLE car_brands ADD CONSTRAINT car_brands_brand_un UNIQUE ( brand );

CREATE TABLE car_models (
    "id"           INTEGER NOT NULL,
    "model"        VARCHAR2(255) NOT NULL,
    car_brand_id INTEGER NOT NULL,
    car_type_id  INTEGER NOT NULL
);

ALTER TABLE car_models ADD CONSTRAINT car_models_pk PRIMARY KEY ( "id" );

ALTER TABLE car_models ADD CONSTRAINT car_models_model_un UNIQUE ( "model" );

CREATE TABLE car_offers (
    "id"            INTEGER NOT NULL,
    price_per_day NUMBER(5,2) NOT NULL,
    car_model_id  INTEGER NOT NULL
);

ALTER TABLE car_offers ADD CONSTRAINT car_offers_pk PRIMARY KEY ( "id" );

CREATE TABLE car_types (
    "id"   INTEGER NOT NULL,
    "type" VARCHAR2(255) NOT NULL
);

ALTER TABLE car_types ADD CONSTRAINT car_types_pk PRIMARY KEY ( "id" );

ALTER TABLE car_types ADD CONSTRAINT car_types_type_un UNIQUE ( "type" );

CREATE TABLE cities (
    "id"          INTEGER NOT NULL,
    "name"        VARCHAR2(20) NOT NULL,
    postal_code VARCHAR2(5) NOT NULL,
    country_id  INTEGER NOT NULL
);

ALTER TABLE cities ADD CONSTRAINT cities_pk PRIMARY KEY ( "id" );

ALTER TABLE cities ADD CONSTRAINT cities_postal_code__un UNIQUE ( postal_code );

CREATE TABLE countries (
    "id"   INTEGER NOT NULL,
    "name" VARCHAR2(20) NOT NULL
);

ALTER TABLE countries ADD CONSTRAINT countries_pk PRIMARY KEY ( "id" );

ALTER TABLE countries ADD CONSTRAINT countries_name_un UNIQUE ( "name" );

CREATE TABLE customers (
    "id"             INTEGER NOT NULL,
    first_name     VARCHAR2(255) NOT NULL,
    last_name      VARCHAR2(255) NOT NULL,
    phone_number   VARCHAR2(15) NOT NULL,
    street_address VARCHAR2(255) NOT NULL,
    city_id        INTEGER NOT NULL
);

ALTER TABLE customers ADD CONSTRAINT customers_pk PRIMARY KEY ( "id" );

ALTER TABLE customers ADD CONSTRAINT customers_phone_number_un UNIQUE ( phone_number );

CREATE TABLE employees (
    "id"           INTEGER NOT NULL,
    first_name   VARCHAR2(255) NOT NULL,
    last_name    VARCHAR2(255) NOT NULL,
    phone_number VARCHAR2(15) NOT NULL,
    job_title_id INTEGER NOT NULL
);

ALTER TABLE employees ADD CONSTRAINT employees_pk PRIMARY KEY ( "id" );

ALTER TABLE employees ADD CONSTRAINT employees_phone_number_un UNIQUE ( phone_number );

CREATE TABLE job_titles (
    "id"    INTEGER NOT NULL,
    title VARCHAR2(100) NOT NULL
);

ALTER TABLE job_titles ADD CONSTRAINT job_titles_pk PRIMARY KEY ( "id" );

ALTER TABLE job_titles ADD CONSTRAINT job_titles_title_un UNIQUE ( title );

CREATE TABLE rentals (
    "id"           INTEGER NOT NULL,
    "date"       DATE NOT NULL,
    "days"         NUMBER NOT NULL,
    customer_id  INTEGER NOT NULL,
    car_offer_id INTEGER NOT NULL,
    employee_id  INTEGER NOT NULL
);

ALTER TABLE rentals ADD CONSTRAINT rentals_pk PRIMARY KEY ( "id" );

ALTER TABLE rentals ADD CONSTRAINT rentals_date_car_offer_un UNIQUE ( "date",
                                                                car_offer_id );

ALTER TABLE rentals ADD CONSTRAINT rentals_date_customer_un UNIQUE ( "date",
                                                                customer_id );

ALTER TABLE car_models
    ADD CONSTRAINT car_models_car_brands_fk FOREIGN KEY ( car_brand_id )
        REFERENCES car_brands ( "id" );

ALTER TABLE car_models
    ADD CONSTRAINT car_models_car_types_fk FOREIGN KEY ( car_type_id )
        REFERENCES car_types ( "id" );

ALTER TABLE car_offers
    ADD CONSTRAINT car_offers_car_models_fk FOREIGN KEY ( car_model_id )
        REFERENCES car_models ( "id" );

ALTER TABLE cities
    ADD CONSTRAINT cities_countries_fk FOREIGN KEY ( country_id )
        REFERENCES countries ( "id" );

ALTER TABLE customers
    ADD CONSTRAINT customers_cities_fk FOREIGN KEY ( city_id )
        REFERENCES cities ( "id" );

ALTER TABLE employees
    ADD CONSTRAINT employees_job_titles_fk FOREIGN KEY ( job_title_id )
        REFERENCES job_titles ( "id" );

ALTER TABLE rentals
    ADD CONSTRAINT rentals_car_offers_fk FOREIGN KEY ( car_offer_id )
        REFERENCES car_offers ( "id" );

ALTER TABLE rentals
    ADD CONSTRAINT rentals_customers_fk FOREIGN KEY ( customer_id )
        REFERENCES customers ( "id",
                               city_id );

ALTER TABLE rentals
    ADD CONSTRAINT rentals_employees_fk FOREIGN KEY ( employee_id )
        REFERENCES employees ( "id" );

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
INSERT INTO car_models VALUES(12, 'WRONG MODEL', 1, 1);
UPDATE car_models SET "model" = '128i Hatch' WHERE "model" = '118i Hatch';
DELETE FROM car_models WHERE "model" = 'WRONG MODEL';

INSERT INTO car_offers VALUES(11, 75.00, 11);
INSERT INTO car_offers VALUES(10, 90.00, 10);
INSERT INTO car_offers VALUES(9, 160.00, 9);
INSERT INTO car_offers VALUES(8, 110.00, 8);
INSERT INTO car_offers VALUES(7, 120.00, 7);
INSERT INTO car_offers VALUES(6, 85.00, 6);
INSERT INTO car_offers VALUES(5, 115.00, 5);
INSERT INTO car_offers VALUES(4, 110.00, 4);
INSERT INTO car_offers VALUES(3, 70.00, 3);
INSERT INTO car_offers VALUES(2, 100.00, 2);
INSERT INTO car_offers VALUES(1, 90.00, 1);
UPDATE car_offers SET price_per_day = 150.00 WHERE “id” = 8;

INSERT INTO job_titles VALUES(3, 'Owner');
INSERT INTO job_titles VALUES(2, 'Manager');
INSERT INTO job_titles VALUES(1, 'Salesman');
UPDATE job_titles SET title = 'CEO' WHERE title = 'Owner';

INSERT INTO employees VALUES(3, 'Jenkins', 'Hart', '+359878000007', 3);
INSERT INTO employees VALUES(2, 'Alvarez', 'Gutierrez', '+359878000006', 2);
INSERT INTO employees VALUES(1, 'Ruiz', 'Cruz', '+359878000005', 1);
UPDATE employees SET phone_number = '+359878000008' WHERE employee_id = 3;

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
INSERT INTO rentals VALUES(13, '12/15/2022', 5, 2, 2, 3);

SELECT brand AS "brand", "model", "type", price_per_day AS "price per day"
FROM car_offers 
JOIN car_models
ON car_offers.car_model_id = car_models.id
JOIN car_brands
ON car_models.car_brand_id = car_brands.id
JOIN car_types
ON car_models.car_type_id = car_types.id
WHERE brand = :input OR "type" = :input OR "model" = :input;

SELECT brand AS "brand", "model", "type", price_per_day AS "price per day"
FROM car_offers 
JOIN car_models
ON car_offers.car_model_id = car_models.id
JOIN car_brands
ON car_models.car_brand_id = car_brands.id
JOIN car_types
ON car_models.car_type_id = car_types.id
WHERE price_per_day <= :input;

SELECT brand AS "brand", "model", "type", price_per_day AS "price per day", first_name AS "first name", last_name AS "last name", "date" 
FROM rentals
JOIN employees
ON rentals.employee_id = employees.id
JOIN car_offers
ON rentals.car_offer_id = car_offers.id
JOIN car_models
ON car_offers.car_model_id = car_models.id
JOIN car_brands
ON car_models.car_brand_id = car_brands.id
JOIN car_types
ON car_models.car_type_id = car_types.id
WHERE first_name = :first_name AND last_name = :last_name
ORDER BY "type" ASC, "date" ASC;

SELECT *
FROM (
    SELECT "date", "days", employees.first_name AS "employee first name", employees.last_name AS "employee last name",
    customers.first_name AS "customer first name", customers.last_name AS "customer last name",
    car_brands.brand AS "brand", car_models."model" AS "model", car_offers.price_per_day AS "price per day"
    FROM rentals
    JOIN customers
    ON rentals.customer_id = customers.id
    JOIN employees
    ON rentals.employee_id = employees.id
    JOIN car_offers
    ON rentals.car_id = car_offers.id
    JOIN car_models
    ON car_offers.car_model_id = car_models.id
    JOIN car_brands
    ON car_models.car_brand_id = car_brands.id
    JOIN car_brands
    ON car_models.car_brand_id = car_brands.id
    JOIN car_types
    ON car_models.car_type_id = car_types.id
    ORDER BY rentals.id DESC
)
WHERE ROWNUM <= 10
ORDER BY "days" ASC;

SELECT brand AS "brand", "model", "type", price_per_day AS "price per day", first_name AS "first name", last_name AS "last name", phone_number AS "phone_number", "date"
FROM rentals
JOIN customers
ON rentals.customer_id = customers.id
JOIN car_offers
ON rentals.car_offer_id = car_offers.id
JOIN car_models
ON car_offers.car_model_id = car_models.id
JOIN car_brands
ON car_models.car_brand_id = car_brands.id
JOIN car_types
ON car_models.car_type_id = car_types.id
WHERE first_name = :first_name AND last_name = :last_name
ORDER BY "date" ASC;

SELECT brand AS "brand", "model", "type", price_per_day AS "price per day", first_name AS "first name", last_name AS "last name", phone_number AS "phone_number", "date"
FROM rentals
JOIN customers
ON rentals.customer_id = customers.id
JOIN car_offers
ON rentals.car_offer_id = car_offers.id
JOIN car_models
ON car_offers.car_model_id = car_models.id
JOIN car_brands
ON car_models.car_brand_id = car_brands.id
JOIN car_types
ON car_models.car_type_id = car_types.id
WHERE "date" >= to_date(:start_period,'DD-MM-YYYY') AND "date" <= to_date(:end_period,'DD-MM-YYYY')
ORDER BY first_name ASC, last_name ASC;
