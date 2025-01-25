package com.e_commerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce.models.Category;
import com.e_commerce.models.Products;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long> {

	List<Products> findByCategory(Category category);

	List<Products> findAllByOrderByProductPriceAsc();
	
	List<Products> findAllByOrderByProductPriceDesc();

	

}
