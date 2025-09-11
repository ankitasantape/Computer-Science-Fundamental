package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BasicStreamsExercise03 {

    /**
     * Exercise 3: In a list of products, find a product that is cheaper than a price limit.
     *
     * @param products   A list of product.
     * @param priceLimit The price limit.
     * @return An Optional containing a product from the list that is cheaper than the price limit,
     * or an empty Optional if there is no such product in the list.
     */
    public Optional<Product> findProductCheaperThan(List<Product> products, BigDecimal priceLimit) {
        // TODO: Find any product in the list that is cheaper than the given price limit.
        //
        // Hint: You'll need to add two stream operations.

        return products.stream()
                .filter(product -> product.getPrice().compareTo(priceLimit) < 0) // keep only cheaper ones
                .findAny();
    }

    public static void main(String[] args) {
        BasicStreamsExercise03 exercise = new BasicStreamsExercise03();
        List<Product> products = ExampleData.getProducts();

        // Case 1: Find product cheaper than 10
        BigDecimal priceLimit1 = new BigDecimal("10");
        Optional<Product> result1 = exercise.findProductCheaperThan(products, priceLimit1);
        System.out.println("Product cheaper than " + priceLimit1 + ": " +
                result1.map(Product::getName).orElse("None found"));

        // Case 2: Find product cheaper than 2
        BigDecimal priceLimit2 = new BigDecimal("2");
        Optional<Product> result2 = exercise.findProductCheaperThan(products, priceLimit2);
        System.out.println("Product cheaper than " + priceLimit2 + ": " +
                result2.map(Product::getName).orElse("None found"));

        // Case 3: Find product cheaper than 100 (should definitely find one)
        BigDecimal priceLimit3 = new BigDecimal("100");
        Optional<Product> result3 = exercise.findProductCheaperThan(products, priceLimit3);
        System.out.println("Product cheaper than " + priceLimit3 + ": " +
                result3.map(Product::getName).orElse("None found"));
    }
}
