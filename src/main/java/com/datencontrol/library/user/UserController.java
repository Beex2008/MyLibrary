package com.datencontrol.library.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping(value="/users")
    public ResponseEntity addUser(@RequestBody @Valid User user){
    User userOld = new User("lewis.koua@outlook.fr");
        return new ResponseEntity(userOld, HttpStatus.CREATED);
    }
}
