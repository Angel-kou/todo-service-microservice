ALTER TABLE Todo ADD COLUMN user_id int ;

alter table Todo add foreign key(user_id) references user(id);


