package com.example.momgoDB.mongoDB.service;

import com.example.momgoDB.mongoDB.collection.Person;
import com.example.momgoDB.mongoDB.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public String savePerson(Person person) {
        return personRepository.save(person).getPersonId();
    }
}
