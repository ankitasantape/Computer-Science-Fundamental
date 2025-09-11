package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.List;
import java.util.stream.Collectors;

public class BasicStreamsExercise06 {

    /**
     * Exercise 6: Format a list of products into a string, with one product per line.
     *
     * @param products A list of products.
     * @return A string containing the products as strings, separated by newlines ('\n').
     */
    public String formatProductList(List<Product> products) {
        // TODO: Convert each of the products to a string (using Product.toString()) and collect the results in a string.
        // Separate the products strings by a newline character '\n'.
        //
        // Hint: Use the appropriate collector in the last step to convert the product strings into a single string.

        return products.stream()
                .map(Product::toString)            // convert each Product to String
                .collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        BasicStreamsExercise06 exercise = new BasicStreamsExercise06();
        List<Product> products = ExampleData.getProducts();

        String result = exercise.formatProductList(products);

        System.out.println("Formatted Product List:");
        System.out.println("-----------------------");
        System.out.println(result);
    }
}
