CREATE TABLE user(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) unique ,
    password VARCHAR(255),
    deleted boolean default false,
    created_date DATE
) engine=InnoDB DEFAULT CHARSET = UTF8;