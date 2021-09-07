package com.coding.exercise.persondetails.service;

import com.coding.exercise.persondetails.model.Address;
import com.coding.exercise.persondetails.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @InjectMocks
    AddressServiceImpl addressService;

    @Mock
    AddressRepository addressRepository;

    @Before()
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAddressByIdTest()
    {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address1));

        Address address = addressService.findById(1L);

        assertEquals("Strret1", address.getStreet());
        assertEquals("CityA", address.getCity());
        assertEquals("PostalCode1", address.getPostalCode());
    }

    @Test
    public void createAddressTest()
    {
        Address address1 = Address.builder().id(1L).city("CityA").postalCode("PostalCode1").state("State1").street("Strret1").build();
        addressService.createAddress(address1);
        verify(addressRepository, times(1)).save(address1);
    }
}
