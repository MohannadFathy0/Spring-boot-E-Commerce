package com.fog.e_commerce.cart;

import com.fog.e_commerce.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "_cartItem")
public class CartItem {

    @Id
    @GeneratedValue
    private Long id;
    private Long cartId;
    @ManyToOne
    private Product product;
    private int quantity;


    public CartItem() {
    }

    public CartItem(Long cartId, Product product, int quantity) {
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
    }



    public Long getId() {
        return id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
