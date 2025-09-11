package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvancedStreamsExercise02 {

    /**
     * Exercise 2: Count the number of products per category.
     *
     * @param products A list of products.
     * @return A Map containing categories as keys, and the number of product for each category as values.
     */
    public Map<Category, Long> countProductsPerCategory(List<Product> products) {
        // TODO: Group the products by category and count the number of products in each category.
        //
        // Hint: Remember how grouping works. It splits the input stream into multiple streams.
        // You can use a downstream collector to process the output streams of the grouping operation further.
        // For counting the number of products, look at the API documentation of class Collectors - there's a factory method that you can use.

        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,  // Group by the category of the product
                        Collectors.counting()  // Count the number of products in each group
                ));
    }

    public static void main(String[] args) {
        AdvancedStreamsExercise02 exercise = new AdvancedStreamsExercise02();
        List<Product> products = ExampleData.getProducts();

        Map<Category, Long> result = exercise.countProductsPerCategory(products);

        System.out.println("Products per category:");
        result.forEach((category, count) ->
                System.out.println(category + " -> " + count));
    }
}
