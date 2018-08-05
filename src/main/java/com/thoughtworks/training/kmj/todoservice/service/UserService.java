package com.thoughtworks.training.kmj.todoservice.service;

import com.thoughtworks.training.kmj.todoservice.model.User;
import com.thoughtworks.training.kmj.todoservice.repository.UserRepository;
import com.thoughtworks.training.kmj.todoservice.utils.Constants;
import com.thoughtworks.training.kmj.todoservice.utils.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {



    @Autowired
    UserRepository userRepository;

    public ResponseEntity create(User user) {
        Optional<User> user1 = userRepository.findOneByName(user.getName());
        if (user1.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Constants.USERNAME_CONFLICT);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("password");
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.CREATE_SUCCESS);

    }


    public List<User> find() {
        return userRepository.findAll();
    }

    public boolean verify(String username, String password) {
        return userRepository.findOneByName(username)
                .map(User::getPassword)
                .filter(password::equals)
                .isPresent();
    }


    public int findIdByName(String username) {
        return userRepository.findIdByName(username).getId();
    }

    public ResponseEntity login(User user) {
        if(verify(user.getName(),user.getPassword())){
            int id = findIdByName(user.getName());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(JwtAuthentication.generateToken(id));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.USERNAME_OR_PASSWORD_ERROR);
    }

    public User findUser(Integer id){
        return userRepository.findOne(id);
    }


}
