package com.acme.streamslambdas.examples;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfacesExample03 {

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

        String name = "Spaghetti";
        // convert product to bigdecimal
        Function<Product, BigDecimal> productToPrice = Product::getPrice;

       // convert bigdecimal to string
        Function<BigDecimal, String> priceToMessage = price
                -> "The price is " + price + " EUR";
        // Compose a new function out of the two functions above by using andThen(...)
        // Chain function - Product -> Bigdecimal -> String
        Function<Product, String> productToMessage = productToPrice.andThen(priceToMessage);

        // Alternative: use compose(...), which is the same as andThen(...) except that the order of the functions is reversed
//        Function<Product, String> productToMessage = priceToMessage.compose(productToPrice);

        findProduct(products, p -> p.getName().equals(name))
                .map(productToMessage)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Product not found")
                );

    }
}