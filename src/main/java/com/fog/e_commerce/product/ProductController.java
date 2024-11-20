package com.fog.e_commerce.product;

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
    private Iterable<Product> getAllProduct(
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size);
        return gender != null
                ? service.findAllProductsByGender(gender, paging)
                : service.getAllProduct(paging);
    }

    @GetMapping("/id/{id}")
    private Product getProduct(@PathVariable Long id) {
        return service.getProduct(id);
    }

//    @GetMapping("/gender/{gender}")
//    private List<Product> getAllProductsByGender(@PathVariable String gender) {
//        return service.findAllProductsByGender(gender);
//    }

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
