package com.e_commerce.models;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId; 
	
	@Column(nullable = false)
	private String productName; 
	
	@Column(nullable = false)
	private String productDescription;
	
	@Column(nullable = false)
	private Double productPrice;
	
	@ElementCollection
	@CollectionTable(name="product_images", joinColumns = @JoinColumn(name="product_id"))
	private List<String> productImages; 
	
	@Enumerated(EnumType.STRING)
	private Category category; 
	
//	@ManyToOne
//	@JoinColumn(name="retailer_id")
//	private Retailer retailer;
	
	@Column(nullable = false)
	private Long productStock;
	
	@Column(nullable = true)
	private String additionalInformation;

}
