package com.thoughtworks.training.kmj.userservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thoughtworks.training.kmj.userservice.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TimeTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSerializeUserEntity() throws JsonProcessingException {
        User user = User.builder()
                .id(1)
                .name("kmj")
                .password("123456")
                .build();
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        String jsonStr = objectMapper.writeValueAsString(user);

        System.out.println(jsonStr);


    }
}
