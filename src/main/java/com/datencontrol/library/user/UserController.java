package com.datencontrol.library.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    UserRepository userRepository;


    @PostMapping(value="/users")
    public ResponseEntity addUser(@RequestBody @Valid UserInfo user){

        //User userOld = new User("lewis.koua@outlook.fr");
        List<UserInfo> users = userRepository.findByEmail(user.getEmail());
        if(!users.isEmpty()){
            return new ResponseEntity("Users already existing", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
