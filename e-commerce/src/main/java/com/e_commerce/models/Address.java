package com.e_commerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Column(insertable=false, updatable=false)
    private String fullName;

    @Column(insertable=false, updatable=false)
    private String phoneNumber;
    
    @Column(insertable=false, updatable=false)
    private String email;

    @Column(insertable=false, updatable=false)
    private String addressLine;

    @Column(insertable=false, updatable=false)
    private String city;

    @Column(insertable=false, updatable=false)
    private String state;

    @Column(insertable=false, updatable=false)
    private String postalCode;

    @Column(insertable=false, updatable=false)
    private String country;
}