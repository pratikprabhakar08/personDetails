package com.coding.exercise.persondetails.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    @NotEmpty(message = "Street is mandatory")
    private String street;

    @Column(name = "city")
    @NotEmpty(message = "City is mandatory")
    private String city;

    @Column(name = "state")
    @NotEmpty(message = "State is mandatory")
    private String state;

    @Column(name = "postalCode")
    @NotEmpty(message = "Postal Code is mandatory")
    private String postalCode;
}
