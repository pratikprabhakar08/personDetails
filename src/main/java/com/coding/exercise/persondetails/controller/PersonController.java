package com.coding.exercise.persondetails.controller;

import com.coding.exercise.persondetails.model.Person;
import com.coding.exercise.persondetails.service.PersonService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@Validated
public class PersonController {

    PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPerson() {
        List<Person> persons = personService.findAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping({"/{personId}"})
    public ResponseEntity<Person> getPersonDetails(@PathVariable Long personId) {
        return new ResponseEntity<>(personService.findById(personId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        Person personData = personService.createPerson(person);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("person", "/api/v1/person/" + personData.getId().toString());
        return new ResponseEntity<>(personData, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{personId}"})
    public ResponseEntity<Person> updatePerson(@PathVariable("personId") Long personId, @RequestBody Person person) {
        personService.updatePerson(personId, person);
        return new ResponseEntity<>(personService.findById(personId), HttpStatus.OK);
    }

    @DeleteMapping({"/{personId}"})
    public ResponseEntity<Person> deletePerson(@PathVariable("personId") Long personId) {
        personService.deleteById(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
