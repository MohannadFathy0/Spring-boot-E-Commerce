package com.fog.e_commerce;

import com.fog.e_commerce.product.Product;
import com.fog.e_commerce.product.ProductRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final Faker faker = new Faker();

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.findAll().stream().count() < 25) {
            for (int i = 0; i < 25; i++) { // Generate 25 fake products
                Product product = new Product(
                        faker.commerce().productName(),               // name
                        faker.commerce().material() + " " + faker.lorem().sentence(), // description
                        faker.options().option("Male", "Female"), // gender
                        faker.internet().image(),                     // image URL
                        faker.options().option("S", "M", "L", "XL"),  // size
                        faker.options().option("T-Shirts", "Jeans", "Jackets & Coats", "Skirts"), // category
                        Double.parseDouble(faker.commerce().price()), // price
                        faker.number().numberBetween(1, 100),         // quantity
                        faker.number().numberBetween(0, 50),          // sellingCount
                        faker.date().past(365, java.util.concurrent.TimeUnit.DAYS) // date
                );

                productRepository.save(product); // Save each product to the database
            }
        }
    }
}

