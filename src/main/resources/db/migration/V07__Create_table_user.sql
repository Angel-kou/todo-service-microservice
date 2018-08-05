CREATE TABLE user(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    deleted boolean default false
) engine=InnoDB DEFAULT CHARSET = UTF8;