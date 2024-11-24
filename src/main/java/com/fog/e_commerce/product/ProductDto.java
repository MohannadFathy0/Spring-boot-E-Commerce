package com.fog.e_commerce.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class ProductDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Image cannot be null")
    private String image;

    @NotNull(message = "Size cannot be null")
    private String size;

    @NotNull(message = "Category cannot be null")
    private String category;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private Double price;

    @Min(value = 0, message = "Quantity must be zero or greater")
    private Integer quantity;

    @Min(value = 0, message = "Selling count must be zero or greater")
    private Integer sellingCount;

    @NotNull(message = "Date cannot be null")
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
