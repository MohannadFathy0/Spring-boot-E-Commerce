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

    public Page<Product> getAllProduct(Pageable pageable) {
        Page<Product> product = repository.findAll(pageable);
        return product;
    }

    public Product getProduct(Long id) {
        Product product = repository.findById(id).orElseThrow();
        return product;
    }

    public ResponseEntity addProduct(ProductDto dto) {
        Product product = new Product(dto.getName() ,dto.getDescription(), dto.getGender(), dto.getImage(),
                dto.getSize(), dto.getCategory(), dto.getPrice(), dto.getQuantity(), dto.getSellingCount(), dto.getDate());

        repository.save(product);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow();
        repository.delete(product);
        return ResponseEntity.ok("Product " + product.getId().toString() + " deleted.");
    }

    public ResponseEntity updateProduct(Long id, ProductDto dto) {
        Product product = repository.findById(id).orElseThrow();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        repository.save(product);

        return ResponseEntity.ok(product);
    }

    public List<Product> findAllProductsByGender(String gender, Pageable pageable){
        List<Product> products = repository.findByGender(gender, pageable);
        return products;
    }
}
