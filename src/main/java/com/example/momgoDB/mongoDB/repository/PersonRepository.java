package com.example.momgoDB.mongoDB.repository;

import com.example.momgoDB.mongoDB.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    //repo of type person and type of primary_key is String.
    //work just similar to JPA Repository.

    //custom methods like this.
    List<Person> findByFirstNameStartsWith(String name);

    //List<Person> findByAgeBetween(Integer min , Integer max); //named method , but we will now see same with Query.

    @Query(value="{'age' : {$gt : ?0 , $lt : ?1}}" , fields = "{addresses : 0}") //age field should be greater than 1st parameter and less that 2nd parameter.
    //fields which are set to 0 will not come in output , 1 means chahiye 0 means nahi chahiye.
    List<Person> getByPersonAge(Integer minAge , Integer maxAge);


}
