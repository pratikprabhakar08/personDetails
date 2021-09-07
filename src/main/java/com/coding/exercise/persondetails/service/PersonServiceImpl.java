package com.coding.exercise.persondetails.service;

import com.coding.exercise.persondetails.exception.PersonCreationException;
import com.coding.exercise.persondetails.exception.PersonNotFoundException;
import com.coding.exercise.persondetails.model.Person;
import com.coding.exercise.persondetails.repository.AddressRepository;
import com.coding.exercise.persondetails.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Person createPerson(Person person) {
        if (person == null) {
            throw new PersonCreationException("Person details cannot be null");
        }
        else {
            addressRepository.save(person.getAddress());
            return personRepository.save(person);
        }
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found."));
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        Person exists = personRepository.findById(id).get();
        if (exists != null) {
            Person personExists = personRepository.findById(id).get();
            personExists.setFirstName(person.getFirstName());
            personExists.setLastName(person.getLastName());
            personExists.setAddress(person.getAddress());
            addressRepository.save(personExists.getAddress());
            return personRepository.save(personExists);
        } else {
            throw new PersonNotFoundException("Person not found for id: "+id);
        }
    }

    @Override
    public void deleteById(Long id) {
        boolean exists = personRepository.existsById(id);
        if (exists) {
            Person person = personRepository.findById(id).get();
            personRepository.delete(person);
        } else {
            throw new PersonNotFoundException("Cannot find person to delete");
        }
    }

    @Override
    public List<Person> findAllPersons() {
        List<Person> persons = personRepository.findAll();
        if (persons.isEmpty()) {
            throw new PersonNotFoundException("Cannot find any person details");
        } else {
            return persons;
        }
    }

    @Override
    public Long countTotalPersons() {
        return personRepository.count();
    }
}
