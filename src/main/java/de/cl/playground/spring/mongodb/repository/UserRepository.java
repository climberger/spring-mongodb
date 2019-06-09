package de.cl.playground.spring.mongodb.repository;

import de.cl.playground.spring.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {




}
