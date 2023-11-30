package com.example.momgoDB.mongoDB.service;

import com.example.momgoDB.mongoDB.collection.Person;
import com.example.momgoDB.mongoDB.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{



    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate; //Trying search through this , just like we have jdbcTemplate.

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

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable); //Query & Criteria from the core library.
        List<Criteria> criteria = new ArrayList<>();
        if(name!=null && !name.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(name, "i")); //ese match karke dekho. //small i means case sensitive search.

        }
        if(minAge!=null && maxAge!=null)
        {
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge)); //gte = greater than equal to..
        }
        if(city!=null && !city.isEmpty())
        {
            criteria.add(Criteria.where("addresses.city").is(city)); //see how city is referred , from person output.
        }

        if(!criteria.isEmpty())
        {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0]))); //put all criterias in the first one.
        }

        Page<Person> people = PageableExecutionUtils.getPage(
                mongoTemplate.find(query , Person.class
                ), pageable, ()->mongoTemplate.count(query.skip(0).limit(0),Person.class));

        //find(query) will return list , map to person.class , then pageable will happen on that list , ()-> used to get the count.
        return people;
    }
}
