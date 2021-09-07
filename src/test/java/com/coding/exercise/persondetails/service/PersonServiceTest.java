package com.coding.exercise.persondetails.service;

import com.coding.exercise.persondetails.model.Address;
import com.coding.exercise.persondetails.model.Person;
import com.coding.exercise.persondetails.repository.AddressRepository;
import com.coding.exercise.persondetails.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonServiceTest {

    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    PersonRepository repository;

    @Mock
    AddressRepository addressRepository;

    @Before()
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllPersonsTest()
    {
        List<Person> persons = new ArrayList<>();
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "Abra", address1);
        Address address2 = Address.builder().id(1L).city("CityB").postalCode("PostalCode2").state("State2").street("Strret2").build();
        Person personTwo = new Person(2L, "Johnny", "David", address2);
        Address address3 = Address.builder().id(1L).city("CityC").postalCode("PostalCode3").state("State3").street("Strret3").build();
        Person personThree = new Person(3L, "Raj", "Kumar", address3);

        persons.add(personOne);
        persons.add(personTwo);
        persons.add(personThree);

        when(repository.findAll()).thenReturn(persons);

        //test
        List<Person> personList = personService.findAllPersons();

        assertEquals(3, personList.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getPersonByIdTest()
    {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "Abra", address1);
        when(repository.findById(1L)).thenReturn(Optional.of(personOne));

        Person person = personService.findById(1L);

        assertEquals("John", person.getFirstName());
        assertEquals("Abra", person.getLastName());
        assertEquals(address1, person.getAddress());
    }

    @Test
    public void createPersonTest()
    {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "Abra", address1);

        personService.createPerson(personOne);

        verify(addressRepository, times(1)).save(address1);
        verify(repository, times(1)).save(personOne);
    }
}
