package com.coding.exercise.persondetails.service;

import com.coding.exercise.persondetails.model.Person;

import java.util.List;

public interface PersonService {

    Person createPerson(Person person);

    Person findById(Long id);

    Person updatePerson(Long id, Person person);

    void deleteById(Long id);

    List<Person> findAllPersons();

    Long countTotalPersons();
}
