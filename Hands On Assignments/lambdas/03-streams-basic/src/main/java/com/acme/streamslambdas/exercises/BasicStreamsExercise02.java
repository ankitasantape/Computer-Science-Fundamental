package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicStreamsExercise02 {

    /**
     * Exercise 2a: In a list of products, find the products that are in a given category and return their names.
     *
     * @param products A list of products.
     * @param category A product category.
     * @return A List containing the names of the products in the given category.
     */
    public List<String> getProductNamesForCategory(List<Product> products, Category category) {
        // TODO: Add three stream operation methods:
        //
        // 1) Find the products that are in the given category
        // 2) Transform each product to its name
        // 3) Collect the result into a List
        //
        // Hint: Use the API documentation of interface java.util.stream.Stream.

        return products.stream()
                .filter(product -> product.getCategory() == category)
                .map(Product::getName)
                .toList();

    }

    /**
     * Exercise 2b: Given a map that contains products grouped by category and a stream of categories, get the names of the products
     * for each category in that stream and return all the products in a list.
     *
     * @param productsByCategory A map containing products grouped by category.
     * @param categories         A stream of product categories.
     * @return A list containing the names of products in each of the categories in the given stream.
     */
    public List<String> categoriesToProductNames(Map<Category, List<Product>> productsByCategory, Stream<Category> categories) {
        // TODO: Start with the stream of categories.
        // For each category in that stream, get the products.
        // Then transform them to product names.
        // Finally, collect the results in a list.
        //
        // Hint: You'll need to use different mapping methods.

//        return categories
//                .flatMap(category -> productsByCategory.getOrDefault(category, List.of()).stream())
//                .map(Product::getName)
//                .toList();
        return categories
                .flatMap(category -> productsByCategory
                        .getOrDefault(category, Collections.emptyList())
                        .stream())
                .map(Product::getName)
                .collect(Collectors.toList());

   }

    public static void main(String[] args) {
        BasicStreamsExercise02 exercise = new BasicStreamsExercise02();
        List<Product> products = ExampleData.getProducts();

//        Test Exercise 2a
        System.out.println("Products in UTENSILS:");
        List<String> utensilNames = exercise.getProductNamesForCategory(products, Category.UTENSILS);
        utensilNames.forEach(System.out::println);

//       Prepare data for Exercise 2b
        Map<Category, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        System.out.println("\nProducts in FOOD and OFFICE:");
        List<String> foodAndOfficeNames = exercise.categoriesToProductNames(
                productsByCategory,
                Stream.of(Category.FOOD, Category.OFFICE)
        );
        foodAndOfficeNames.forEach(System.out::println);
    }
}
