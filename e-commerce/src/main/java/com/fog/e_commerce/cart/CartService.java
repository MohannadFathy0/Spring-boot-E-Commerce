package com.fog.e_commerce.cart;

import com.fog.e_commerce.product.Product;
import com.fog.e_commerce.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository repository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository repository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public Cart findCartById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));

    }

    public List<Cart> getAllCart() {
        return repository.findAll();
    }

    public Cart addCart(Long userId) {
        Cart cart = new Cart(userId, 0, new ArrayList<>());
        repository.save(cart);
        return cart;
    }

    public void deleteCart(Long id) {
        Cart cart = repository.findById(id).orElseThrow();
        repository.delete(cart);
    }

    public void addCartItem(Long cartId, Long productId, int quantity) {
        // Retrieve the cart and product from the database
        Cart cart = repository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItem = cart.getProducts().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        int cartQuantity = existingCartItem.map(CartItem::getQuantity).orElse(0);
        int availableProductQuantity = product.getQuantity();

        // Check if the available product quantity is greater than or equal to the quantity already in the cart
        if (cartQuantity + quantity > availableProductQuantity) {
            throw new IllegalStateException("Cannot add more items. Available quantity in stock is: " + availableProductQuantity);
        }

        if (existingCartItem.isPresent()) {
            // If the product is already in the cart, increase the quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // If the product is not in the cart, create a new CartItem and add it to the cart
            CartItem cartItem = new CartItem(cart.getId(), product, quantity);
            cartItemRepository.save(cartItem);
            cart.getProducts().add(cartItem);
        }
        cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));

        // Save the cart with the updated item
        repository.save(cart);
    }

    // Reduce product quantity or remove CartItem if quantity reaches 0
    public void reduceProductQuantity(Long cartId, Long cartItemId, int quantity) {
        Cart cart = repository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cart.getProducts().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        // Reduce the quantity
        int newQuantity = cartItem.getQuantity() - quantity;

        if (newQuantity > 0) {
            // If the quantity is greater than 0, update the quantity
            cartItem.setQuantity(newQuantity);
        } else {
            // If the quantity is 0 or less, remove the CartItem
            cart.removeItem(cartItem);
            cartItemRepository.delete(cartItem);  // Remove the CartItem from the repository
        }
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProduct().getPrice() * quantity));

        repository.save(cart);  // Save the updated cart
    }

    public void deleteCartItem(Long cartId, Long cartItemId) {
        Cart cart = repository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cart.getProducts().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));
        cart.removeItem(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        repository.save(cart);
    }
}
