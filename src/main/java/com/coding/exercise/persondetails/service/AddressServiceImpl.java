package com.coding.exercise.persondetails.service;

import com.coding.exercise.persondetails.exception.AddressCreationException;
import com.coding.exercise.persondetails.exception.AddressNotFoundException;
import com.coding.exercise.persondetails.model.Address;
import com.coding.exercise.persondetails.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address createAddress(Address address) {
        if (address == null) {
            throw new AddressCreationException("Address cannot be null");
        }
        else {
            return addressRepository.save(address);
        }
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException("Address Not found."));
    }

    @Override
    public Address updateAddress(Long addressId, Address address) {
        Address exists = addressRepository.findById(addressId).get();
        if (exists != null) {
            Address addressExists = addressRepository.findById(addressId).get();
            addressExists.setCity(address.getCity());
            addressExists.setPostalCode(address.getPostalCode());
            addressExists.setStreet(address.getStreet());
            return addressRepository.save(addressExists);
        } else {
            throw new AddressNotFoundException("Address not found for id: "+addressId);
        }
    }

    @Override
    public void deleteById(Long id) {
        boolean exists = addressRepository.existsById(id);
        if (exists) {
            Address address = addressRepository.findById(id).get();
            addressRepository.delete(address);
        } else {
            throw new AddressNotFoundException("Cannot find address to delete");
        }
    }
}
