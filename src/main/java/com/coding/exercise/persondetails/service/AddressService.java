package com.coding.exercise.persondetails.service;

import com.coding.exercise.persondetails.model.Address;

public interface AddressService {

    Address createAddress(Address address);

    Address findById(Long id);

    Address updateAddress(Long addressId, Address address);

    void deleteById(Long id);
}
