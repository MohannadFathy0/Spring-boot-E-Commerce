package com.fog.e_commerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Product product = repository.findById(id).orElseThrow();
        return product;
    }

    //Admin Methods
    public ResponseEntity addProduct(ProductDto dto) {
        Product product = new Product(dto.getName() ,dto.getDescription(), dto.getGender(), dto.getImage(),
                dto.getSize(), dto.getCategory(), dto.getPrice(), dto.getQuantity(), dto.getSellingCount(), dto.getDate());

        repository.save(product);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity updateProduct(Long id, ProductDto dto) {
        Product product = repository.findById(id).orElseThrow();
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
        Product product = repository.findById(id).orElseThrow();
        repository.delete(product);
        return ResponseEntity.ok("Product " + product.getId().toString() + " deleted.");
    }
}
