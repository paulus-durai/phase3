-- SQL script to initialize the H2 database schema and insert sample data for the ABC Telecom Postpaid Billing System

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NULL,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    phone_number VARCHAR(30),
    CONSTRAINT fk_customers_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Services table
CREATE TABLE IF NOT EXISTS services (
    service_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    service_type VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    CONSTRAINT fk_services_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Usage records table
CREATE TABLE IF NOT EXISTS usage_records (
    usage_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_id BIGINT NOT NULL,
    usage_date DATE NOT NULL,
    usage_amount DECIMAL(14,4) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    CONSTRAINT fk_usage_service FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE IF NOT EXISTS invoices (
    invoice_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    amount DECIMAL(14,2) NOT NULL,
    due_date DATE,
    status VARCHAR(30) NOT NULL,
    CONSTRAINT fk_invoice_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(14,2) NOT NULL,
    payment_method VARCHAR(50),
    CONSTRAINT fk_payment_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
);

-- Sample bcrypt password hashes (placeholders) for demo users
-- Note: Replace with generated bcrypt values for production
-- Roles and users will be created at application startup using BCrypt
-- See Application startup runner for demo user creation (admin/customer1)

INSERT INTO customers (user_id, full_name, email, address, phone_number) VALUES
(NULL, 'John Doe', 'john.doe@example.com', '123 Elm Street', '1234567890'),
(NULL, 'Jane Smith', 'jane.smith@example.com', '456 Oak Avenue', '0987654321');

-- Sample services
INSERT INTO services (customer_id, service_type, start_date, status) VALUES
(1, 'POSTPAID_MOBILE', '2023-01-01', 'ACTIVE'),
(2, 'POSTPAID_BROADBAND', '2023-02-15', 'ACTIVE');

-- Sample usage records
INSERT INTO usage_records (service_id, usage_date, usage_amount, unit) VALUES
(1, '2023-11-01', 120.50, 'MINUTES'),
(1, '2023-11-15', 2.75, 'GB'),
(2, '2023-11-10', 350.00, 'GB');

INSERT INTO invoices (customer_id, account_id, amount, due_date, status) VALUES
(1, 0, 75.50, '2023-12-30', 'PENDING'),
(2, 0, 150.00, '2023-12-30', 'PENDING');

-- Sample payments
INSERT INTO payments (invoice_id, amount, payment_method) VALUES
(1, 25.50, 'CREDIT_CARD'),
(2, 150.00, 'BANK_TRANSFER');

-- Note: User accounts (passwords) are created by the application on startup
-- to ensure BCrypt-hashed passwords and correct role associations.