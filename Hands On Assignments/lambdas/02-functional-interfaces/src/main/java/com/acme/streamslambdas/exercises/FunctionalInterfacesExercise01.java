package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FunctionalInterfacesExercise01 {

    /**
     * Exercise 1: Filter a list of products.
     * <p>
     * Choose a standard functional interface for the parameter 'f'.
     * Which functional interface is appropriate here? (Function, Consumer, Supplier, Predicate, ...)
     * <p>
     * Implement the method.
     *
     * @param products List of products to filter. (Note: The method should not modify this list).
     * @param f        Determines which products should be in the result.
     * @return A filtered list of products.
     */
    public List<Product> filterProducts(List<Product> products, /* TODO: Replace 'Object' with a functional interface */ Predicate<Product> f) {
        List<Product> result = new ArrayList<>();

        // TODO: Implement this method. Loop through the list of products, call 'f' to determine if a product should be
        // in the result list, and put it in the result list if appropriate.
        for (Product product : products){
            if(f.test(product)){
               result.add(product);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FunctionalInterfacesExercise01 exercise = new FunctionalInterfacesExercise01();

        List<Product> products = ExampleData.getProducts();

        // Example 1: Filter products cheaper than 5.00
        List<Product> cheapProducts = exercise.filterProducts(
                products,
                p -> p.getPrice().doubleValue() < 5.00
        );
        System.out.println("Cheap products (< 5): " + cheapProducts);

        // Example 2: Filter products from category FOOD
        List<Product> foodProducts = exercise.filterProducts(
                products,
                p -> p.getCategory() == Category.FOOD
        );
        System.out.println("Food products: " + foodProducts);

        // Example 3: Filter products with names starting with "C"
        List<Product> cProducts = exercise.filterProducts(
                products,
                p -> p.getName().startsWith("C")
        );
        System.out.println("Products starting with C: " + cProducts);
    }
}