package com.thoughtworks.training.kmj.todoservice.repository;


import com.google.common.collect.ImmutableList;
import com.thoughtworks.training.kmj.todoservice.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {

    public List<Person> list() {
        return ImmutableList.of(
                new Person("kmj","xian"),
                new Person("haha","pucheng"),
                new Person("lin","shanghai")

        );

    }
}
