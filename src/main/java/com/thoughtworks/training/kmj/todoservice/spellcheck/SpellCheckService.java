package com.thoughtworks.training.kmj.todoservice.spellcheck;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtworks.training.kmj.todoservice.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpellCheckService {

    @Autowired
    private SpellChecker spellChecker;

//    @Retryable(maxAttempts = 2,backoff = @Backoff(10))
    @HystrixCommand(fallbackMethod = "checkFallback")
    public List<ToDo> check(List<ToDo> list) {
        spellChecker.check(list, ToDo::getContent, ToDo::setSuggestion);
        return list;
    }

    public List<ToDo> checkFallback(List<ToDo> list){
        System.out.println("-------checkFallback-----------");
        return list;
    }



//    @Recover
//    public List<ToDo> onFailure(RuntimeException e,List<ToDo> list){
//        System.out.println("-------onFailure-----------");
//        return list;
//    }


}
