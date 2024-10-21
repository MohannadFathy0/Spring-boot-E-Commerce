package com.fog.e_commerce.cart;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    private Cart getCartById(@PathVariable Long id) {
        return service.findCartById(id);
    }

    @GetMapping
    private List<Cart> getAllCarts(){
        return service.getAllCart();
    }

    @PostMapping
    private void addCartItem(@RequestBody CartItemDto dto) {
        service.addCartItem(dto.getCartId(), dto.getProductId(), dto.getQuantity());
    }

    @PostMapping("/{cartId}/reduce")
    public void reduceProductQuantity(@PathVariable Long cartId, @RequestBody ReduceQuantityRequest request) {
        service.reduceProductQuantity(cartId, request.getCartItemId(), request.getQuantity());
    }

    @DeleteMapping("/{cartId}/remove")
    private void deleteCartItem(@PathVariable Long cartId, @RequestParam Long cartItemId) {
        service.deleteCartItem(cartId, cartItemId);
    }
}
