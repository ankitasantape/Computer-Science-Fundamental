package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvancedStreamsExercise04 {

    /**
     * Exercise 4: Split the products into food and non-food products and get their names.
     *
     * @param products A list of products.
     * @return A map containing the names of the food products and the non-food products.
     */
    public Map<Boolean, List<String>> getFoodAndNonFoodProductNames(List<Product> products) {
        // TODO: Partition the products into food and non-food products, and transform them into product names.
        //
        // Return a map where the food product names are under the key 'true' and the non-food product names are under the key 'false'.
        //
        // Hint: How do you map the products to product names after partitioning them?

        return products.stream()
                .collect(Collectors.partitioningBy(
                        product -> product.getCategory() == Category.FOOD, // true if food
                        Collectors.mapping(Product::getName, Collectors.toList())
                ));
    }

    public static void main(String[] args) {
        AdvancedStreamsExercise04 exercise = new AdvancedStreamsExercise04();
        List<Product> products = ExampleData.getProducts();

        Map<Boolean, List<String>> result = exercise.getFoodAndNonFoodProductNames(products);

        System.out.println("Food Products: " + result.get(true));
        System.out.println("Non-Food Products: " + result.get(false));
    }
}
