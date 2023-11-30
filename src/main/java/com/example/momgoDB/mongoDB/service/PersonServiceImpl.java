package com.example.momgoDB.mongoDB.service;

import com.example.momgoDB.mongoDB.collection.Person;
import com.example.momgoDB.mongoDB.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{



    @Autowired
    private PersonRepository personRepository;

    @Override
    public String savePerson(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonStartWith(String name) {
        return personRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public void deleteById(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getByPersonAge(Integer minAge, Integer maxAge) {
        return personRepository.getByPersonAge(minAge , maxAge);
    }
}
