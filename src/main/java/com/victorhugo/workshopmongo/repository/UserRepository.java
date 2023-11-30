package com.victorhugo.workshopmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.victorhugo.workshopmongo.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByEmail(String email);
	User findByEmailAndPassword(String email,String password);
}
