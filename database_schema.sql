-- Create Database
CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

-- Create Students Table
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    date_of_birth DATE,
    enrollment_date DATE DEFAULT (CURRENT_DATE),
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    status ENUM('Active', 'Inactive', 'Graduated') DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert Sample Data
INSERT INTO students (student_id, first_name, last_name, email, phone, date_of_birth, major, gpa, status) VALUES
('S001', 'John', 'Doe', 'john.doe@email.com', '1234567890', '2002-05-15', 'Computer Science', 3.75, 'Active'),
('S002', 'Jane', 'Smith', 'jane.smith@email.com', '9876543210', '2001-08-22', 'Mathematics', 3.92, 'Active'),
('S003', 'Mike', 'Johnson', 'mike.j@email.com', '5551234567', '2003-03-10', 'Physics', 3.45, 'Active');
