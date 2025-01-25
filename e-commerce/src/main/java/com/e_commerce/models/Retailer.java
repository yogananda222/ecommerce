package com.e_commerce.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Retailer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long retailerId;
	
	@Column(nullable = false, unique = true)
	private String gstNumber;
	
	@Column(nullable = false, unique = true)
	private String phoneNumber;
	
	@Column(nullable=false, unique = true)
	private String email;
	
	@OneToMany
	@JsonManagedReference
	private List<Products> products;
	
	private boolean approved; 
	
	private boolean blocked; 

}
