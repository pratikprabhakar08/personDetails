package com.coding.exercise.persondetails.controller;

import com.coding.exercise.persondetails.model.Address;
import com.coding.exercise.persondetails.service.AddressService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@Validated
public class AddressController {

    AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping({"/{addressId}"})
    public ResponseEntity<Address> getAddressDetails(@PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.findById(addressId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> saveAddress(@RequestBody Address address) {
        Address addressData = addressService.createAddress(address);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("address", "/api/v1/address/" + addressData.getId().toString());
        return new ResponseEntity<>(addressData, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{addressId}"})
    public ResponseEntity<Address> updateAddress(@PathVariable("addressId") Long addressId, @RequestBody Address address) {
        addressService.updateAddress(addressId, address);
        return new ResponseEntity<>(addressService.findById(addressId), HttpStatus.OK);
    }

    @DeleteMapping({"/{addressId}"})
    public ResponseEntity<Address> deleteAddress(@PathVariable("addressId") Long addressId) {
        addressService.deleteById(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
