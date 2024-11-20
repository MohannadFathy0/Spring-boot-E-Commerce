package com.fog.e_commerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("")
    private ResponseEntity<Page<Product>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String category) {

        Pageable paging = PageRequest.of(page, size);
        Page<Product> products = service.getProducts(gender, category, paging);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/id/{id}")
    private Product getProduct(@PathVariable Long id) {
        return service.getProduct(id);
    }

    // Admin methods

    @PostMapping("/")
    private ResponseEntity addProduct(@RequestBody ProductDto dto) {
        return service.addProduct(dto);
    }

    @PostMapping("/{id}")
    private ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        return service.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteProduct(@PathVariable Long id){
        return service.deleteProduct(id);
    }
}
