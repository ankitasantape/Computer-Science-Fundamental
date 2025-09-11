package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;

public class BasicStreamsExercise05 {

    /**
     * Exercise 5: Check if all office products in the given list of products cost less than a price limit.
     *
     * @param products   A list of products.
     * @param priceLimit The price limit.
     * @return {@code true} if all products in the category OFFICE in the list cost less than the price limit,
     * {@code false} otherwise.
     */
    public boolean areAllOfficeProductsCheap(List<Product> products, BigDecimal priceLimit) {
        // TODO: Look for products in the category OFFICE. Check if all of them cost less than the price limit.
        //
        // Hint: Use two stream operations; one the find the appropriate products,
        // and another one to check if they all cost less than the price limit.

        return products.stream()
                .filter(product -> product.getCategory() == Category.OFFICE) // keep only OFFICE products
                .allMatch(product -> product.getPrice().compareTo(priceLimit) < 0);

    }

    public static void main(String[] args) {
        BasicStreamsExercise05 exercise = new BasicStreamsExercise05();
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("20.00");

        boolean result = exercise.areAllOfficeProductsCheap(products, priceLimit);

        if (result) {
            System.out.println("All office products are cheaper than " + priceLimit);
        } else {
            System.out.println("Some office products are NOT cheaper than " + priceLimit);
        }
    }
}
