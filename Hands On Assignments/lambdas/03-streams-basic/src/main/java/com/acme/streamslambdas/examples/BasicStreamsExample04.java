package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BasicStreamsExample04 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // findFirst() will return an Optional with the first element in the stream, or an empty Optional
        // This is often used together with filter() to search for an element on arbitrary criteria
        Optional<Product> firstExpensiveProduct = products.stream()
                .filter(p -> p.getPrice().doubleValue() > 10.0)
                .findFirst();

        System.out.println("First product with price > 10: " + firstExpensiveProduct);

        // If you only want to check if the stream contains an element that matches your search criteria,
        // then you can use anyMatch(), which will return a boolean result
        // Unlike findFirst() and findAny(), you don't have to filter first - anyMatch() takes a Predicate

        // To check if all elements of the stream match specific criteria, use allMatch()

        // noneMatch() returns the opposite of anyMatch()

        // anyMatch() checks if at least one element matches
        boolean hasCheapProduct = products.stream()
                .anyMatch(p -> p.getPrice().doubleValue() < 2.0);

        System.out.println("Any product with price < 2: " + hasCheapProduct);

        // allMatch() checks if all elements match
        boolean allOfficeProducts = products.stream()
                .allMatch(p -> p.getCategory() == Category.OFFICE);

        System.out.println("All products belong to OFFICE category: " + allOfficeProducts);

        // noneMatch() checks if no element matches
        boolean noneCleaningAbove20 = products.stream()
                .noneMatch(p -> p.getCategory() == Category.CLEANING &&
                        p.getPrice().doubleValue() > 20.0);

        System.out.println("No CLEANING product costs more than 20: " + noneCleaningAbove20);
    }
}
