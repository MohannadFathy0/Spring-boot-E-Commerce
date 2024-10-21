package com.fog.e_commerce.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "_product")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String gender;
    private String image;
    private String size;
    // Enum
    private String category;
    private Double price;
    private int quantity;
    private int sellingCount;
    private Date date;

    public Product() {
    }

    public Product(String name, String description, String gender, String image, String size,
                   String category, Double price, int quantity, int sellingCount, Date date) {
        this.name = name;
        this.description = description;
        this.gender = gender;
        this.image = image;
        this.size = size;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.sellingCount = sellingCount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSellingCount() {
        return sellingCount;
    }

    public void setSellingCount(int sellingCount) {
        this.sellingCount = sellingCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
