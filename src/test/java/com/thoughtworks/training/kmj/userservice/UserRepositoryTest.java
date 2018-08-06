package com.thoughtworks.training.kmj.userservice;


import com.thoughtworks.training.kmj.userservice.model.User;
import com.thoughtworks.training.kmj.userservice.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnUserWithName() {

        userRepository.save(new User(1,"user1","123"));
        Optional<User> user = userRepository.findOneByName("user1");
        assertTrue(user.isPresent());
        assertThat(user.get().getName(),is("user1"));
    }
}
