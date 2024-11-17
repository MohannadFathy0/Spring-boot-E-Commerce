package com.fog.e_commerce.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Content-Type, Authorization")
    @GetMapping("/id/{id}")
    private Product getProduct(@PathVariable Long id) {
        return service.getProduct(id);
    }

    @GetMapping("/gender/{gender}")
    private List<Product> getAllProductsByGender(@PathVariable String gender) {
        return service.findAllProductsByGender(gender);
    }

    // Admin methods

    @PostMapping("/")
    private ResponseEntity addProduct(@RequestBody ProductDto dto) {
        return service.addProduct(dto);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteProduct(@PathVariable Long id){
        return service.deleteProduct(id);
    }

    @PostMapping("/{id}")
    private ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        return service.updateProduct(id, dto);
    }
}
