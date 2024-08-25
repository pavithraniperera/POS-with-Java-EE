
USE pos_system;

CREATE TABLE customers (

                           id VARCHAR(15) PRIMARY KEY ,
                           name VARCHAR(100) NOT NULL,
                           contact VARCHAR(15),
                           address VARCHAR(255),
                           note TEXT
);

CREATE TABLE items (
                       id  VARCHAR(15) PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       quantity INT NOT NULL,
                       category VARCHAR(50),
                       description TEXT,
                       imgSrc VARCHAR(255)
);

CREATE TABLE orders (
                        orderId VARCHAR(15) PRIMARY KEY,
                        customerId VARCHAR(15),
                        total DECIMAL(10, 2) NOT NULL,
                        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (customerId) REFERENCES customers(id)
);

-- Junction Table: Order_Items
CREATE TABLE order_items (
                             orderId VARCHAR(15),
                             itemId VARCHAR(15),
                             quantity INT NOT NULL,
                             FOREIGN KEY (orderId) REFERENCES orders(orderId),
                             FOREIGN KEY (itemId) REFERENCES items(id)

);

CREATE TABLE users (
                       userId INT PRIMARY KEY AUTO_INCREMENT,
                       userName VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL

);