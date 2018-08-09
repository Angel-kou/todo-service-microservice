CREATE TABLE Task(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255),
    todo_id INT,
    constraint `fk_todo_id` FOREIGN KEY (`todo_id`) REFERENCES `Todo` (`id`) on delete cascade on update cascade

) engine=InnoDB DEFAULT CHARSET = UTF8;