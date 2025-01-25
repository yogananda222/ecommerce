package com.e_commerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.e_commerce.exceptions.ProductNotFoundException;
import com.e_commerce.models.Category;
import com.e_commerce.models.Products;
import com.e_commerce.service.ProductServiceImpl;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private ProductServiceImpl productService;
	
	@PostMapping("/add")
	public ResponseEntity<Products> addProduct(
	        @RequestParam("productName") String productName,
	        @RequestParam("productDescription") String productDescription,
	        @RequestParam("productPrice") double productPrice,
	        @RequestParam("category") String category,
	        @RequestParam("productStock") long productStock,
	        @RequestParam(value = "additionalInformation", required = false) String additionalInformation,
	        @RequestParam("productImages") List<MultipartFile> productImages) {
	    try {
	        Products newProduct = new Products();
	        newProduct.setProductName(productName);
	        newProduct.setProductDescription(productDescription);
	        newProduct.setProductPrice(productPrice);
	        newProduct.setCategory(Category.valueOf(category));
	        newProduct.setProductStock(productStock);
	        newProduct.setAdditionalInformation(additionalInformation);

	        Products savedProduct = productService.addProduct(newProduct, productImages);
	        
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	        
	    } catch (IllegalArgumentException e) {
	    	
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    } catch (Exception e) {
	        
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

    
    @GetMapping("/allProducts")
    public ResponseEntity<List<Products>> viewProducts() {
        try {
            List<Products> products = productService.viewProducts();

            if (products.isEmpty()) {
                return ResponseEntity.noContent().build(); 
            }
            return new ResponseEntity<>(products,HttpStatus.OK); 
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


     @GetMapping("/{productId}")
     public ResponseEntity<Optional<Products>> getProductById(@PathVariable Long productId) {
    	
    	 try {
    		 
    		 Optional<Products> product = Optional.of(productService.getProductById(productId).orElseThrow(()-> new 
    				 ProductNotFoundException("Product with id"+ productId+ " not found")));
			 
			 return new ResponseEntity<>(product, HttpStatus.OK);
			 
		 }catch(ProductNotFoundException e) {
			 
			 throw e;
    		 
    		 
    	 }catch(Exception e) {
    		 
    		 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    		 
    	 }
    	 
      }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product with ID " + productId + " has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + productId + " not found.");
        }
    }

 
    @GetMapping("/count")
    public ResponseEntity<Long> productsCount() {
        long count = productService.productsCount();
        return ResponseEntity.ok(count);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<?> productsByCategory(@PathVariable String category) {
    	
        try {
        	
            Category categoryEnum = Category.valueOf(category.toUpperCase());

            List<Products> products = productService.productsByCategory(categoryEnum);

            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }


            return ResponseEntity.ok(products);

        } catch (IllegalArgumentException e) {
        	
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid category: " + category);
        }
    }


    
    @GetMapping("/sorted/price/asc") // price low to high
    public ResponseEntity<List<Products>> getProductsSortedByPriceAsc() {
        List<Products> products = productService.getProductsSortedByPriceAsc();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted/price/desc") // price high to low 
    public ResponseEntity<List<Products>> getProductsSortedByPriceDesc() {
        List<Products> products = productService.getProductsSortedByPriceDesc();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
    
    
    @PutMapping("/{productId}/stock")
    public ResponseEntity<?> updateStock(
            @PathVariable long productId,
            @RequestParam Long productStock) {
        try {
            // Call the service to update stock
            Products updatedProduct = productService.updateStock(productId, productStock);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{productId}/images")
    public ResponseEntity<?> updateProductImages(
            @PathVariable long productId,
            @RequestParam("images") List<MultipartFile> newImages) {
        try {
            // Call the service to update product images
            Products updatedProduct = productService.updateProductImages(productId, newImages);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{productId}/price")
    public ResponseEntity<?> updateProductPrice(
            @PathVariable long productId,
            @RequestParam("newPrice") Double newPrice) {
        try {
            // Call the service to update product price
            Products updatedProduct = productService.updateProductPrice(productId, newPrice);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/paginated")
    public ResponseEntity<Page<Products>> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
	    
    	return new ResponseEntity<>(productService.getAllProducts(page, size), HttpStatus.OK);
    }
    

}
