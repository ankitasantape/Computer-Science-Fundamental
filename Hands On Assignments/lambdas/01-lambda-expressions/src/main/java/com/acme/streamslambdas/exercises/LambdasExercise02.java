package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.ArrayList;
import java.util.List;

public class LambdasExercise02 {

    public interface ProductFilter {
        boolean accept(Product product);
    }

    /**
     * Exercise 2: Find products by category by implementing a functional interface using a lambda expression and calling it.
     *
     * @param products The list of products to search.
     * @param category The category of products to search for.
     * @return A new list containing the products which are in the specified category.
     */
    public List<Product> findProductsByCategory(List<Product> products, Category category) {
        // TODO: Implement interface ProductFilter with a lambda expression
        // The lambda expression should return true if the product is in the given category
        ProductFilter filter = product -> product.getCategory() == category; // TODO: Replace 'null' by a lambda expression

        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            // TODO: Add products that are accepted by the filter to the 'result' list
            if(filter.accept(product)){
                result.add(product);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        LambdasExercise02 example = new LambdasExercise02();

        List<Product> products = ExampleData.getProducts();

        // Pick a category to filter
        Category category = Category.FOOD;

        List<Product> foodProducts = example.findProductsByCategory(products, category);

        System.out.println("Products in category " + category + ":");
        foodProducts.forEach(System.out::println);
    }
}
