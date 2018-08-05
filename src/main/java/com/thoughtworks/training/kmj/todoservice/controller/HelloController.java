package com.thoughtworks.training.kmj.todoservice.controller;

import com.thoughtworks.training.kmj.todoservice.model.Person;
import com.thoughtworks.training.kmj.todoservice.service.HelloService;
import com.thoughtworks.training.kmj.todoservice.service.ToDoService;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.swing.*;


@Controller
public class HelloController {



    @Autowired
    private HelloService helloService;

    @RequestMapping(method = RequestMethod.GET,path="/hello/{name}")
    public String hello(@PathVariable("name") String name,
                        Model model){
//        Person person = Person.builder()
//                .name(name)
//                .build();

        model.addAttribute("person",helloService.find(name));
        return "hello";
    }


    @RequestMapping(method = RequestMethod.GET,path="/hello/{name}/{from}")
    public String hello1(@PathVariable("name") String name,
                         @PathVariable("from") String from,
                        Model model){
        Person person = Person.builder()
                .name(name)
                .from(from)
                .build();

        model.addAttribute("person",person);
        return "hello";
    }


}
