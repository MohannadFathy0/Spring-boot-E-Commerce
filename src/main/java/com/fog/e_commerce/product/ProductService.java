package com.fog.e_commerce.product;

import com.fog.e_commerce.exception.InvalidInputException;
import com.fog.e_commerce.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Page<Product> getProducts(String gender, String category, Pageable pageable) {
        return repository.findAllByFilters(gender, category, pageable);
    }

    public Product getProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with ID " + id + " not found"));
        return product;
    }

    //Admin Methods
    public ResponseEntity addProduct(@Valid ProductDto dto) {
        // Optionally, check if product with similar name or category exists
        if (repository.existsByName(dto.getName())) {
            throw new InvalidInputException("Product with this name already exists", "Add Product");
        }

        Product product = new Product(dto.getName(), dto.getDescription(), dto.getGender(), dto.getImage(),
                dto.getSize(), dto.getCategory(), dto.getPrice(), dto.getQuantity(), dto.getSellingCount(), dto.getDate());

        repository.save(product);
        return ResponseEntity.ok(product);
    }


    public ResponseEntity updateProduct(Long id, ProductDto dto) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

        if (dto.getSellingCount() > dto.getQuantity()) {
            throw new InvalidInputException("Selling count cannot exceed available quantity", "Update Product");
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setGender(dto.getGender());
        product.setImage(dto.getImage());
        product.setSize(dto.getSize());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setSellingCount(dto.getSellingCount());
        product.setDate(dto.getDate());

        repository.save(product);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity deleteProduct(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

        repository.delete(product);
        return ResponseEntity.ok("Product with ID " + id + " deleted.");
    }

}
