package com.fog.e_commerce.cart;

import com.fog.e_commerce.product.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_cart")
public class Cart {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private double totalPrice;
    @OneToMany
    private List<CartItem> products = new ArrayList<>();


    public Cart() {
    }

    public Cart(Long userId, double totalPrice, List<CartItem> products) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public void addItem(Product product, int quantity) {
        CartItem cartItem = new CartItem(this.id, product, quantity);
        products.add(cartItem);
    }

    public void removeItem(CartItem cartItem) {
        products.remove(cartItem);
    }
}
