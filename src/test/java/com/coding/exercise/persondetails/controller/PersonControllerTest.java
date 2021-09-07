package com.coding.exercise.persondetails.controller;

import com.coding.exercise.persondetails.exception.PersonNotFoundException;
import com.coding.exercise.persondetails.model.Address;
import com.coding.exercise.persondetails.model.Person;
import com.coding.exercise.persondetails.service.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonServiceImpl personService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetPersons() throws Exception {
        List<Person> personList = new ArrayList<>();
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "Abra", address1);
        personList.add(personOne);

        Mockito.when(personService.findAllPersons()).thenReturn(personList);
        mockMvc.perform(get("/api/v1/person")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.equalTo("John")));
    }

    @Test
    public void testPostPerson() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "Abra", address1);
        Mockito.when(personService.createPerson(ArgumentMatchers.any())).thenReturn(personOne);
        String json = mapper.writeValueAsString(personOne);
        mockMvc.perform(post("/api/v1/person").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.firstName", Matchers.equalTo("John")));
    }

    @Test
    public void testPutPerson() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("Postal1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "David", address1);

        Mockito.when(personService.updatePerson(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(personOne);
        String json = mapper.writeValueAsString(personOne);
        mockMvc.perform(put("/api/v1/person/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("Postal1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "David", address1);
        Mockito.when(personService.findById(1L)).thenReturn(personOne);
        Mockito.doNothing().when(personService).deleteById(ArgumentMatchers.anyLong());
        MvcResult requestResult = mockMvc.perform(delete("/api/v1/person/1"))
                .andExpect(status().isNoContent()).andExpect(status().isNoContent()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        Assert.assertEquals(result, "");
    }

    @Test
    public void testPostPersonThrowsException() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "Abra", address1);
        Mockito.when(personService.createPerson(ArgumentMatchers.any())).thenThrow(PersonNotFoundException.class);
        String json = mapper.writeValueAsString(personOne);
        mockMvc.perform(post("/api/v1/person").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostAddressThrowsCreationException() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Person personOne = new Person(1L, "John", "Abra", address1);
        Mockito.when(personService.createPerson(ArgumentMatchers.any())).thenThrow(PersonNotFoundException.class);
        String json = mapper.writeValueAsString(personOne);
        mockMvc.perform(post("/api/v1/person").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}