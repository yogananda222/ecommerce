package com.e_commerce.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.e_commerce.models.Category;
import com.e_commerce.models.Products;
import com.e_commerce.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService{
	
	
	@Autowired
	private ProductRepo productRepo;
	
	 private static final String IMAGE_UPLOAD_DIR = "D:/NANDAS ECOMMERCE/e-commerce/images";

	@Override
	public Products addProduct(Products product, List<MultipartFile> productImages) {
		
		List<String> imagePaths = new ArrayList<>();
		
		for(MultipartFile image: productImages) {
			
			try {
				
				String imagePath = saveImage(image);
				
				imagePaths.add(imagePath);
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
		}
		
		product.setProductImages(imagePaths);

		return productRepo.save(product);
	}

    private String saveImage(MultipartFile image) throws IOException {
        String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File destinationFile = new File(IMAGE_UPLOAD_DIR + "/" + imageName);
        image.transferTo(destinationFile);

        return imageName;
    }
    
    
    
	@Override
	public List<Products> viewProducts() {
		return productRepo.findAll();
	}
	
	
	
	public Page<Products> getAllProducts(int page, int size){
		
		Pageable pageable = PageRequest.of(page, size);
		
		return productRepo.findAll(pageable);
		
	}

	@Override
	public Optional<Products> getProductById(Long productId) {
		return productRepo.findById(productId);
	}

	@Override
	public void deleteProduct(Long productId) {
		productRepo.deleteById(productId);
	}


	@Override
	public long productsCount() {
		return productRepo.count();
	}

	@Override
	public List<Products> productsByCategory(Category category) {
		return productRepo.findByCategory(category);
	}

	@Override
	public List<Products> getProductsSortedByPriceAsc() {
		return productRepo.findAllByOrderByProductPriceAsc();
	}

	@Override
	public List<Products> getProductsSortedByPriceDesc() {
		return productRepo.findAllByOrderByProductPriceDesc();
	}



	public Products updateStock(long productId, Long productStock) {
		
		 Optional<Products> existingProductOptional = productRepo.findById(productId);
		 
		 if (existingProductOptional.isPresent()) {
	            Products existingProduct = existingProductOptional.get();
	            
	            existingProduct.setProductStock(productStock);
	            
	            return productRepo.save(existingProduct);
	        } else {
	            throw new RuntimeException("Product with ID " + productId + " not found.");
	        }
	}
	
    @Override
    public Products updateProductImages(long productId, List<MultipartFile> newImages) {
        // Retrieve the product by ID
        Optional<Products> existingProductOptional = productRepo.findById(productId);

        if (existingProductOptional.isPresent()) {
            Products existingProduct = existingProductOptional.get();

            // Save new images
            List<String> imagePaths = new ArrayList<>();
            for (MultipartFile image : newImages) {
                try {
                    String imagePath = saveImage(image);
                    imagePaths.add(imagePath);
                } catch (IOException e) {
                    throw new RuntimeException("Error while uploading image: " + e.getMessage());
                }
            }

            // Update the product's images
            existingProduct.setProductImages(imagePaths);

            // Save the updated product
            return productRepo.save(existingProduct);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }
    }
    
    @Override
    public Products updateProductPrice(long productId, Double newPrice) {
        // Retrieve the product by ID
        Optional<Products> existingProductOptional = productRepo.findById(productId);

        if (existingProductOptional.isPresent()) {
            Products existingProduct = existingProductOptional.get();

            // Update the price if the new price is provided
            if (newPrice != null) {
                existingProduct.setProductPrice(newPrice);
            } else {
                throw new IllegalArgumentException("New price must not be null.");
            }

            // Save the updated product
            return productRepo.save(existingProduct);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }
    }
}
