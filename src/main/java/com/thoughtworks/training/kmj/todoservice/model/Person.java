package com.thoughtworks.training.kmj.todoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class Person {

//    @Getter
    private String name;

//    @Getter
    private  String from;


//    public Person(String name, String from) {
//         this.name = name;
//         this.from = from;
//
//    }
////
//    public String getName() {
//        return name;
//    }
//
//    public String getFrom() {
//        return from;
//    }

}
