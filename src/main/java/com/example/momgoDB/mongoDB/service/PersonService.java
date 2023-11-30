package com.example.momgoDB.mongoDB.service;

import com.example.momgoDB.mongoDB.collection.Person;

import java.util.List;

public interface PersonService {
    public String savePerson(Person p);

    public List<Person> getPersonStartWith(String name);

    public void deleteById(String id);

    List<Person> getByPersonAge(Integer minAge, Integer maxAge);
}
