package com.fog.e_commerce.product;

import java.util.Date;
import java.util.List;

public class ProductDto {

    private String name;
    private String description;
    private String gender;
    private String image;
    private String size;
    private String category;
    private Double price;
    private int quantity;
    private int sellingCount;
    private Date date;

    public ProductDto(String description, String gender, String image, String size,
                      String category, Double price, int quantity, int sellingCount, Date date) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
