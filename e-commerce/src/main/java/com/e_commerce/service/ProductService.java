package com.e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.e_commerce.models.Category;
import com.e_commerce.models.Products;

public interface ProductService {
	
	
	Products addProduct(Products product, List<MultipartFile> productImages);
	
	List<Products> viewProducts(); 
	
	Optional<Products> getProductById(Long productId);
	
	void deleteProduct(Long productId);

	long productsCount();
	
	List<Products> productsByCategory(Category category);
	
	List<Products> getProductsSortedByPriceAsc(); 
	
	List<Products> getProductsSortedByPriceDesc();
	
	Products updateStock(long productId, Long productStock);

	Products updateProductImages(long productId, List<MultipartFile> newImages);

	Products updateProductPrice(long productId, Double newPrice);
};
