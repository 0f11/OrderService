DROP TABLE IF EXISTS "orders";
DROP TABLE IF EXISTS "rows";
DROP SEQUENCE IF EXISTS sq;

CREATE SEQUENCE sq START WITH 1;

CREATE TABLE "orders" (
        id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('sq'),
        order_number VARCHAR(255) NOT NULL
    );

CREATE TABLE "rows" (
        id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('sq'),
        item_name VARCHAR(255) NOT NULL,
        quantity INTEGER,
        price INTEGER
    );