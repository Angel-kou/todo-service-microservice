package com.thoughtworks.training.kmj.todoservice.service;

import com.thoughtworks.training.kmj.todoservice.model.ToDo;
import com.thoughtworks.training.kmj.todoservice.repository.ToDoRepository;
import com.thoughtworks.training.kmj.todoservice.security.TodoAuthFilter;
import com.thoughtworks.training.kmj.todoservice.utils.Constants;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserService userService;

     public List<ToDo> getList() {
         Integer userId = TodoAuthFilter.getUserId();
         List<ToDo> list = toDoRepository.findAllByUserIdIs(userId);
        return list;
    }



    public ResponseEntity create(ToDo todo) {
//        todo.getTasks().forEach(task -> task.setToDo(todo));
        Integer userId = TodoAuthFilter.getUserId();
        todo.setUser(userService.findUser(userId));
        toDoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.CREATE_SUCCESS);
    }

    public ResponseEntity delete(Integer id) {
         toDoRepository.delete(id);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body(Constants.DELETE_SUCCESS);
    }

    public ToDo update(Integer id) {
        ToDo toDo = toDoRepository.findOne(id);
        toDo.setContent("haha");
        toDoRepository.save(toDo);
        return toDo;
    }

    public List<ToDo> getToDos(boolean completed) {
         return toDoRepository.findAllByCompletedIs(completed);
    }

    @Transactional
    public ToDo find(Integer id) throws NotFoundException {
         return
                 Optional.ofNullable(toDoRepository.findOne(id))
                         .orElseThrow(()->new NotFoundException("not found"));
    }
}
