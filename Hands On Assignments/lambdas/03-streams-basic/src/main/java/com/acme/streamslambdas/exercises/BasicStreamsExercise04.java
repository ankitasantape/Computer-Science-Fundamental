package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BasicStreamsExercise04 {

    /**
     * Exercise 4: In a list of products, find the cheapest cleaning product.
     *
     * @param products A list of products.
     * @return An Optional containing the cheapest product in the category CLEANING in the list,
     * or an empty Optional if there is no such product in the list.
     */
    public Optional<Product> findCheapestCleaning(List<Product> products) {
        // TODO: Look for products that are in the category CLEANING and find the cheapest one.
        //
        // Hint: Use a terminal operation to find the cheapest product.
        // Look at the API documentation of interface java.util.stream.Stream, find out which operation would be suitable.

        return products.stream()
                .filter(product -> product.getCategory() == Category.CLEANING)
                .min(Comparator.comparing(Product::getPrice));


    }

    public static void main(String[] args) {
        BasicStreamsExercise04 exercise = new BasicStreamsExercise04();
        List<Product> products = ExampleData.getProducts();

        Optional<Product> cheapestCleaning = exercise.findCheapestCleaning(products);

        if (cheapestCleaning.isPresent()) {
            Product product = cheapestCleaning.get();
            System.out.println("Cheapest cleaning product: " + product.getName() +
                    " - Price: " + product.getPrice());
        } else {
            System.out.println("No cleaning products found.");
        }
    }
}
