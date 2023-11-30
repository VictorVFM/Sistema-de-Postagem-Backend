package com.victorhugo.workshopmongo.services;

import com.victorhugo.workshopmongo.domain.User;
import com.victorhugo.workshopmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository repo;

    public boolean credentialsIsCorrect(String email,String password){
       User u =  repo.findByEmailAndPassword(email,password);
       if(u != null){
           return true;
       }

        return false;
    }
}
