package com.e_commerce.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@Column( nullable = false, unique = true)
	private String phoneNumber;
	
	@Column(nullable = false, unique = true)
	private String email; 
	
	@OneToOne(mappedBy="user")
	@JsonManagedReference
	private Cart cart;
	
	@OneToMany(mappedBy="user")
	@JsonManagedReference
	private List<FavoriteProducts> favoriteProducts;
	
	@OneToMany(mappedBy="user")
	@JsonManagedReference
	private List<Orders> orders;
	
	private boolean approved; 
	
	private boolean blocked; 
	

}
