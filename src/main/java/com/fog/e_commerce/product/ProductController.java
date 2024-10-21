package com.fog.e_commerce.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/product/id/{id}")
    private Product getProduct(@PathVariable Long id) {
        return service.getProduct(id);
    }

    @GetMapping("/product/gender/{gender}")
    private List<Product> getAllProductsByGender(@PathVariable String gender) {
        return service.findAllProductsByGender(gender);
    }

    // Admin methods

    @PostMapping("/admin/product")
    private ResponseEntity addProduct(@RequestBody ProductDto dto) {
        return service.addProduct(dto);
    }

    @DeleteMapping("/admin/product/{id}")
    private ResponseEntity deleteProduct(@PathVariable Long id){
        return service.deleteProduct(id);
    }

    @PostMapping("/admin/product/{id}")
    private ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        return service.updateProduct(id, dto);
    }
}
