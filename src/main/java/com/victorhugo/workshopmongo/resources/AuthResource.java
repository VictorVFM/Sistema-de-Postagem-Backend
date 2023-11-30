package com.victorhugo.workshopmongo.resources;

import com.victorhugo.workshopmongo.domain.User;
import com.victorhugo.workshopmongo.dto.UserCredentials;
import com.victorhugo.workshopmongo.dto.UserDTO;
import com.victorhugo.workshopmongo.services.AuthService;
import com.victorhugo.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

    @Autowired
    private AuthService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void>  credentialsIsCorrect(@RequestBody UserCredentials obj){
        boolean isCorrect = service.credentialsIsCorrect(obj.getEmail(), obj.getPassword());

        if (isCorrect) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
