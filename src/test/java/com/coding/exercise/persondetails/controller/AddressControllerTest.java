package com.coding.exercise.persondetails.controller;

import com.coding.exercise.persondetails.exception.AddressCreationException;
import com.coding.exercise.persondetails.exception.AddressNotFoundException;
import com.coding.exercise.persondetails.model.Address;
import com.coding.exercise.persondetails.service.AddressServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AddressServiceImpl addressService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testPostAddress() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Mockito.when(addressService.createAddress(ArgumentMatchers.any())).thenReturn(address1);
        String json = mapper.writeValueAsString(address1);
        mockMvc.perform(post("/api/v1/address").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.city", Matchers.equalTo("CityA")));
    }

    @Test
    public void testPutAddress() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("Postal1").state("State1").street("Strret1").build();

        Mockito.when(addressService.updateAddress(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(address1);
        String json = mapper.writeValueAsString(address1);
        mockMvc.perform(put("/api/v1/address/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteAddress() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("Postal1").state("State1").street("Strret1").build();
        Mockito.when(addressService.findById(1L)).thenReturn(address1);
        Mockito.doNothing().when(addressService).deleteById(ArgumentMatchers.anyLong());
        MvcResult requestResult = mockMvc.perform(delete("/api/v1/address/1"))
                .andExpect(status().isNoContent()).andExpect(status().isNoContent()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        Assert.assertEquals(result, "");
    }

    @Test
    public void testPostAddressThrowsException() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Mockito.when(addressService.createAddress(ArgumentMatchers.any())).thenThrow(AddressNotFoundException.class);
        String json = mapper.writeValueAsString(address1);
        mockMvc.perform(post("/api/v1/address").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostAddressThrowsCreationException() throws Exception {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        Mockito.when(addressService.createAddress(ArgumentMatchers.any())).thenThrow(AddressCreationException.class);
        String json = mapper.writeValueAsString(address1);
        mockMvc.perform(post("/api/v1/address").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}