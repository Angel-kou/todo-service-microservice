package com.thoughtworks.training.kmj.todoservice.controller;

import com.thoughtworks.training.kmj.todoservice.model.ToDo;
import com.thoughtworks.training.kmj.todoservice.service.ToDoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToDoController {

    @Autowired
    private ToDoService todoService;

    @RequestMapping(method = RequestMethod.GET,path = "/todos")
    public List<ToDo> search() {
        return todoService.getList();
    }

    @GetMapping("/todos/")
    public List<ToDo> searchByCompleted(@RequestParam(required = false ,
            defaultValue = "false") boolean completed) {
        return todoService.getToDos(completed);
    }

    @GetMapping("/todos/{id}")
    public ToDo find(@PathVariable Integer id) throws NotFoundException {
        return todoService.find(id);
    }


    @PostMapping("/todos")
    public ResponseEntity create(@RequestBody ToDo todo)  {
        return todoService.create(todo);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity delete(@PathVariable Integer id)  {

        return todoService.delete(id);
    }

    @PutMapping(value = "/todos/update/{id}")
    public ToDo update(@PathVariable Integer id) {
        return todoService.update(id);
    }


}
