package com.thoughtworks.training.kmj.userservice.service;

import com.thoughtworks.training.kmj.userservice.model.User;
import com.thoughtworks.training.kmj.userservice.repository.UserRepository;
import com.thoughtworks.training.kmj.userservice.utils.Constants;
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
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
//        user.setCreated_date(Instant.now());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.CREATE_SUCCESS);

    }


    public List<User> find() {
        return userRepository.findAll();
    }

    public boolean verify(String username, String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.findOneByName(username)
                .map(User::getPassword)
                .filter(pwd -> encoder.matches(password, pwd))
                .isPresent();
    }


    public int findIdByName(String username) {
        return userRepository.findIdByName(username).getId();
    }

//    public ResponseEntity login(User user) {
//        System.out.println("name" + user.getName() + "         ,pas   " + user.getPassword());
//        if (verify(user.getName(), user.getPassword())) {
//            int id = findIdByName(user.getName());
//            System.out.println("id   " + id);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body(JwtAuthentication.generateToken(id));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.USERNAME_OR_PASSWORD_ERROR);
//    }


    public User login(User user) {
        User user1 = new User();
        System.out.println("userq--old----" + user.getPassword());
        if (verify(user.getName(), user.getPassword())) {
            user1.setId(findIdByName(user.getName()));
            return user1;
        }
        return user1;
    }

    public User findUser(int id) {
//        Claims claims = JwtAuthentication.validateToken(token);
//        Integer id = JwtAuthentication.getUserId(claims);
        return userRepository.findOne(id);
    }
}
