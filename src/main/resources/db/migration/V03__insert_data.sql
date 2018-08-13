INSERT INTO todo (id,content,completed,readonly) VALUES ( 1, 'todo1',false,true);
INSERT INTO todo (id,content,completed,readonly) VALUES ( 2, 'todo2',false,true);


INSERT INTO task (id,content,todo_id) VALUES ( null, 'task1', 1 );
INSERT INTO task (id,content,todo_id) VALUES ( null, 'task2', 1 );
INSERT INTO task (id,content,todo_id) VALUES ( null, 'task3', 2 );


