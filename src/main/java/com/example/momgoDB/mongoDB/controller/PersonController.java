package com.example.momgoDB.mongoDB.controller;

import com.example.momgoDB.mongoDB.collection.Person;
import com.example.momgoDB.mongoDB.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
