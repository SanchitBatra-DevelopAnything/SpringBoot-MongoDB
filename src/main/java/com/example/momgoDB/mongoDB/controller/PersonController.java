package com.example.momgoDB.mongoDB.controller;

import com.example.momgoDB.mongoDB.collection.Person;
import com.example.momgoDB.mongoDB.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public String savePerson(@RequestBody Person person)
    {
        return personService.savePerson(person);
    }

    @GetMapping
    //Sample Request => http://localhost:8080/person?name=Anis
    public List<Person> getPersonStartWith(@RequestParam("name") String name)
    {
        return personService.getPersonStartWith(name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id)
    {
        personService.deleteById(id);
    }

    @GetMapping("/age")
    public List<Person> getByPersonAge(@RequestParam("minAge") Integer minAge , @RequestParam("maxAge") Integer maxAge)
    {
        return personService.getByPersonAge(minAge , maxAge);
    }

    @GetMapping("/search")
    public Page<Person> search(
            @RequestParam(required = false) String name ,
            @RequestParam(required = false) Integer minAge ,
            @RequestParam(required = false) Integer maxAge ,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    )
    {
        Pageable pageable = PageRequest.of(page , size);
        return personService.search(name , minAge , maxAge , city , pageable);
    }

    @GetMapping("/oldestPerson")
    public List<Document> getOldestPerson()
    {
        //Document from org.bson , cuz mongo stores data in binary json format.
        return personService.getOldestPersonByCity();
    }
}
