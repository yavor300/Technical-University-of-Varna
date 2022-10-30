CREATE TABLE car_brands (
    car_brand_id INTEGER NOT NULL,
    brand        VARCHAR2(20) NOT NULL
);

ALTER TABLE car_brands ADD CONSTRAINT car_brands_pk PRIMARY KEY ( car_brand_id );

CREATE TABLE car_models (
    car_model_id INTEGER NOT NULL,
    "model"      VARCHAR2(20) NOT NULL,
    car_brand_id INTEGER NOT NULL,
    car_type_id  INTEGER NOT NULL
);

ALTER TABLE car_models ADD CONSTRAINT car_models_pk PRIMARY KEY ( car_model_id );

CREATE TABLE car_types (
    car_type_id INTEGER NOT NULL,
    "type"      VARCHAR2(20) NOT NULL
);

ALTER TABLE car_types ADD CONSTRAINT car_types_pk PRIMARY KEY ( car_type_id );

CREATE TABLE cars (
    car_id        INTEGER NOT NULL,
    price_per_day NUMBER(4, 2) NOT NULL,
    car_model_id  INTEGER NOT NULL
);

ALTER TABLE cars ADD CONSTRAINT cars_pk PRIMARY KEY ( car_id );

CREATE TABLE cities (
    city_id     INTEGER NOT NULL,
    "name"      VARCHAR2(20) NOT NULL,
    postal_code VARCHAR2(5) NOT NULL,
    country_id  INTEGER NOT NULL
);

ALTER TABLE cities ADD CONSTRAINT cities_pk PRIMARY KEY ( city_id );

CREATE TABLE countries (
    country_id INTEGER NOT NULL,
    "name"       VARCHAR2(20) NOT NULL
);

ALTER TABLE countries ADD CONSTRAINT countries_pk PRIMARY KEY ( country_id );

CREATE TABLE customers (
    customer_id    INTEGER NOT NULL,
    first_name     VARCHAR2(255) NOT NULL,
    last_name      VARCHAR2(255) NOT NULL,
    phone_number   VARCHAR2(15) NOT NULL,
    street_address VARCHAR2(255) NOT NULL,
    citiy_id       INTEGER NOT NULL
);

ALTER TABLE customers ADD CONSTRAINT customers_pk PRIMARY KEY ( customer_id );

CREATE TABLE employees (
    employee_id   INTEGER NOT NULL,
    first_name    VARCHAR2(15) NOT NULL,
    last_name     VARCHAR2(15) NOT NULL,
    phone_number  VARCHAR2(15) NOT NULL,
    job_titles_id INTEGER NOT NULL
);

ALTER TABLE employees ADD CONSTRAINT employees_pk PRIMARY KEY ( employee_id );

CREATE TABLE job_titles (
    job_title_id INTEGER NOT NULL,
    title        VARCHAR2(50) NOT NULL
);

ALTER TABLE job_titles ADD CONSTRAINT job_titles_pk PRIMARY KEY ( job_title_id );

CREATE TABLE rentals (
    rental_id   INTEGER NOT NULL,
    "date"      DATE NOT NULL,
    "days"      INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    car_id      INTEGER NOT NULL,
    employee_id INTEGER NOT NULL
);

ALTER TABLE rentals ADD CONSTRAINT rentals_pk PRIMARY KEY ( rental_id );

ALTER TABLE car_models
    ADD CONSTRAINT car_models_car_brands_fk FOREIGN KEY ( car_brand_id )
        REFERENCES car_brands ( car_brand_id );

ALTER TABLE car_models
    ADD CONSTRAINT car_models_car_types_fk FOREIGN KEY ( car_type_id )
        REFERENCES car_types ( car_type_id );

ALTER TABLE cars
    ADD CONSTRAINT cars_car_models_fk FOREIGN KEY ( car_model_id )
        REFERENCES car_models ( car_model_id );

ALTER TABLE cities
    ADD CONSTRAINT cities_countries_fk FOREIGN KEY ( country_id )
        REFERENCES countries ( country_id );

ALTER TABLE customers
    ADD CONSTRAINT customers_cities_fk FOREIGN KEY ( citiy_id )
        REFERENCES cities ( city_id );

ALTER TABLE employees
    ADD CONSTRAINT employees_job_titles_fk FOREIGN KEY ( job_titles_id )
        REFERENCES job_titles ( job_title_id );

ALTER TABLE rentals
    ADD CONSTRAINT rentals_cars_fk FOREIGN KEY ( car_id )
        REFERENCES cars ( car_id );

ALTER TABLE rentals
    ADD CONSTRAINT rentals_customers_fk FOREIGN KEY ( customer_id )
        REFERENCES customers ( customer_id );

ALTER TABLE rentals
    ADD CONSTRAINT rentals_employees_fk FOREIGN KEY ( employee_id )
        REFERENCES employees ( employee_id );
