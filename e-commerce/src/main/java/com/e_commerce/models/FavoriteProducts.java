package com.e_commerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteProducts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fvrtId;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products product;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}
