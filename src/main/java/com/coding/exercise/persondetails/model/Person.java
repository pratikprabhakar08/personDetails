package com.coding.exercise.persondetails.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName")
    @NotEmpty(message = "First Name is mandatory")
    private String firstName;

    @Column(name = "lastName")
    @NotEmpty(message = "Last Name is mandatory")
    private String lastName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId", referencedColumnName = "id", nullable = false)
    private Address address;
}
