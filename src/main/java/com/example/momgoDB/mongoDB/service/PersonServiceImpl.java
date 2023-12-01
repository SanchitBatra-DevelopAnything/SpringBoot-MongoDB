package com.example.momgoDB.mongoDB.service;

import com.example.momgoDB.mongoDB.collection.Person;
import com.example.momgoDB.mongoDB.repository.PersonRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
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

    @Override
    public List<Document> getOldestPersonByCity() {
        // we're talking on person but city is in address which is in person.
        //Unwind addresses , Aggregation performed, sort all data based on age , then group on city.
        UnwindOperation unwindOperation = Aggregation.unwind("addresses"); //flattens out all the addresses. unwrapped.
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC , "age"); //sort based on age.
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("OLDEST_PERSON"); //group based on city and take first document. as oldest person as it was sorted in desc already.

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation , sortOperation , groupOperation);

        List<Document> person = mongoTemplate.aggregate(aggregation , Person.class
         , Document.class).getMappedResults(); //aggregation , input person , output document.

        return person;
    }
}
