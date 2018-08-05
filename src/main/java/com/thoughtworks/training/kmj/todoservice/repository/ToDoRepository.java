package com.thoughtworks.training.kmj.todoservice.repository;

import com.thoughtworks.training.kmj.todoservice.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Integer> {

    List<ToDo> findAllByCompletedIs(boolean completed);



    List<ToDo> findAllByUserIdIs(Integer id);

}
