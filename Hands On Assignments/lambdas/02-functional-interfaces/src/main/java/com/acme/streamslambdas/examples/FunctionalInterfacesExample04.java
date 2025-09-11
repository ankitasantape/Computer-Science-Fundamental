package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.Category;
import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FunctionalInterfacesExample04 {

    // Go through a list of products, and return the first product for which the predicate returns true.
    static Optional<Product> findProduct(List<Product> products, Predicate<Product> predicate) {
        for (Product product : products) {
            if (predicate.test(product)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("2.00");

        // Two simple predicates that can be combined using the functional composition methods in interface Predicate.
        Predicate<Product> isFood = p -> p.getCategory() == Category.FOOD;
        Predicate<Product> isCheap = p -> p.getPrice().compareTo(priceLimit) < 0;

        // Combine with and(...)
        Predicate<Product> cheapFood = isFood.and(isCheap);

        findProduct(products, cheapFood)
                .ifPresentOrElse(
                        p -> System.out.println("Found cheap food: " + p.getName() + " (" + p.getPrice() + ")"),
                        () -> System.out.println("No cheap food found")
                );

    }
}