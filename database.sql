CREATE DATABASE storedb;

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY NOT NULL,
    username VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password TEXT NOT NULL,
    email VARCHAR(100) NOT NULL,
    dni INTEGER NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL
);
CREATE SEQUENCE users_seq INCREMENT 1 START 1;

CREATE TABLE clients (
    client_id INTEGER PRIMARY KEY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dni INTEGER NOT NULL,
    address VARCHAR(100) NOT NULL,
    address2 VARCHAR(100),
    phone VARCHAR(50),
    email VARCHAR(100)
);
CREATE SEQUENCE clients_seq INCREMENT 1 START 1;

CREATE TABLE categories (
    category_id INTEGER PRIMARY KEY NOT NULL,
    category_name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL
);
CREATE SEQUENCE categories_seq INCREMENT 1 START 1;

CREATE TABLE payment_types (
    payment_type_id INTEGER PRIMARY KEY NOT NULL,
    payment_name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL
);
CREATE SEQUENCE payment_type_seq INCREMENT 1 START 1;

CREATE TABLE providers (
    provider_id INTEGER PRIMARY KEY NOT NULL,
    ruc VARCHAR(100) NOT NULL,
    prov_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    web VARCHAR(100)
);
CREATE SEQUENCE providers_seq INCREMENT 1 START 1;

CREATE TABLE products (
    product_id INTEGER PRIMARY KEY NOT NULL,
    barcode CHAR(10) NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    stock INTEGER NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    model VARCHAR(50),
    brand VARCHAR(50),
    category_id INT NOT NULL,
    provider_id INT NOT NULL
);
CREATE SEQUENCE products_seq INCREMENT 1 START 1000;

CREATE TABLE sales (
    sale_id INTEGER PRIMARY KEY NOT NULL,
    client_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP(0),
    payment_type_id INTEGER NOT NULL
);
CREATE SEQUENCE sales_seq INCREMENT 1 START 1;

CREATE TABLE sales_details (
    sale_detail_id INTEGER PRIMARY KEY NOT NULL,
    sale_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    sell_price NUMERIC(10, 2) NOT NULL,     --Sell price per unit
    discount NUMERIC(10, 2) DEFAULT 0.00    -- Total discount of sell_price * quantity
);
CREATE SEQUENCE sales_details_seq INCREMENT 1 START 1;

-- Foreign keys
ALTER TABLE products ADD CONSTRAINT products_category_fk
FOREIGN KEY (category_id) REFERENCES categories(category_id);

ALTER TABLE products ADD CONSTRAINT products_provider_fk
FOREIGN KEY (provider_id) REFERENCES providers(provider_id);

ALTER TABLE sales ADD CONSTRAINT sales_client_fk
FOREIGN KEY (client_id) REFERENCES clients(client_id);

ALTER TABLE sales ADD CONSTRAINT sales_user_fk
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE sales ADD CONSTRAINT sales_payment_fk
FOREIGN KEY (payment_type_id) REFERENCES payment_types(payment_type_id);

ALTER TABLE sales_details ADD CONSTRAINT details_sale_fk
FOREIGN KEY (sale_id) REFERENCES sales(sale_id);

ALTER TABLE sales_details ADD CONSTRAINT details_product_fk
FOREIGN KEY (product_id) REFERENCES products(product_id);

CREATE VIEW sales_vw AS 
SELECT 
    s.sale_id, 
    s.date, 
    CONCAT(c.first_name, ' ',c.last_name) AS client, 
    CONCAT(u.first_name, ' ', u.last_name) AS user, 
    COALESCE(SUM(d.sell_price * d.quantity), 0) AS subtotal, 
    COALESCE(SUM(d.discount), 0) AS discount, 
    COALESCE(SUM(d.sell_price * d.quantity - d.discount), 0) AS total, 
    p.payment_name AS payment 
FROM sales s 
    JOIN clients c ON s.client_id = c.client_id 
    JOIN users u ON s.user_id = u.user_id 
    JOIN payment_types p ON s.payment_type_id = p.payment_type_id 
    JOIN sales_details d ON s.sale_id = d.sale_id 
    
GROUP BY s.sale_id, c.client_id, u.user_id, p.payment_type_id;
