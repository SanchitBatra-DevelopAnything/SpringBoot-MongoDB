package com.example.momgoDB.mongoDB.repository;

import com.example.momgoDB.mongoDB.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    //repo of type person and type of primary_key is String.
    //work just similar to JPA Repository.
}
