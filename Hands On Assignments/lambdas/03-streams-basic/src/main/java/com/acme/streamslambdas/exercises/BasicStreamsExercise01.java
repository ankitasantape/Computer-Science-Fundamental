package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.Comparator;
import java.util.List;

public class BasicStreamsExercise01 {

    /**
     * Exercise 1: In a list of products, find the products that are in the category UTENSILS and sort them by name.
     *
     * @param products A list of products.
     * @return A list of products that are utensils, sorted by name.
     */
    public List<Product> findUtensilsSortedByName(List<Product> products) {
        // TODO: Add three stream operation methods:
        //
        // 1) Find the products that are in the category UTENSILS
        // 2) Sort them by name
        // 3) Collect the result into a List
        //
        // Hint: Use the API documentation of interface java.util.stream.Stream.

        return products.stream()
                .filter(product -> product.getCategory() == Category.UTENSILS)
                .sorted(Comparator.comparing(Product::getName))
                .toList();

    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();
        BasicStreamsExercise01 exercise = new BasicStreamsExercise01();

        List<Product> utensils = exercise.findUtensilsSortedByName(products);

        System.out.println("Utensils sorted by name:");
        utensils.forEach(System.out::println);
    }
}
