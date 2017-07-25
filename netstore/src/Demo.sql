DROP DATABASE netstore;
CREATE DATABASE netstore;
USE netstore;
CREATE TABLE categorys(
	id VARCHAR(100) PRIMARY KEY,
	NAME VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(255)
);
SELECT * FROM categorys;
CREATE TABLE books(
	id VARCHAR(100) PRIMARY KEY,
	NAME VARCHAR(100) NOT NULL UNIQUE,
	author VARCHAR(100),
	price FLOAT(8,2),
	path VARCHAR(100),
	photoFileName VARCHAR(100),
	description VARCHAR(255),
	categoryId VARCHAR(100),
	CONSTRAINT category_id_fk FOREIGN KEY(categoryId) REFERENCES categorys(ID)
);


CREATE TABLE books(
	id VARCHAR(100) PRIMARY KEY,
	NAME VARCHAR(100) NOT NULL UNIQUE,
	author VARCHAR(100),
	price FLOAT(8,2),
	path VARCHAR(100),
	photoFileName VARCHAR(100),
	description VARCHAR(255),
	categoryId VARCHAR(100),
	CONSTRAINT category_id_fk FOREIGN KEY(categoryId) REFERENCES categorys(ID)
);

CREATE TABLE customers(
	id VARCHAR(100) PRIMARY KEY,
	usename VARCHAR(100) NOT NULL UNIQUE,
	PASSWORD VARCHAR(100) NOT NULL,
	phone VARCHAR(100) NOT NULL,
	address VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	actived BIT(1),
	CODE VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE orders(
	id VARCHAR(100) PRIMARY KEY,
	orderNum VARCHAR(100) NOT NULL UNIQUE,
	quantity INT,
	amount FLOAT(10,2),
	STATUS INT,
	customersId VARCHAR(100),
	CONSTRAINT customers_id_fk FOREIGN KEY(customersId) REFERENCES customers(id)
);


CREATE TABLE orders_items(
	id VARCHAR(100) PRIMARY KEY,
	quantity INT,
	price FLOAT(10,2),
	booksId VARCHAR(100),
	ordersId VARCHAR(100),
	CONSTRAINT books_id_fk FOREIGN KEY(booksId) REFERENCES books(id),
	CONSTRAINT orders_id_fk FOREIGN KEY(ordersId) REFERENCES orders(id)
);
















