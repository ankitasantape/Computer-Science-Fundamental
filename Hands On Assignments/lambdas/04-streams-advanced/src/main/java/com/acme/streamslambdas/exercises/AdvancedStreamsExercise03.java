package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.ExampleData;
import com.acme.streamslambdas.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvancedStreamsExercise03 {

    public enum PriceRange {
        CHEAP, MEDIUM, EXPENSIVE
    }

    /**
     * Exercise 3: Group products by price range.
     *
     * @param products       A list of products.
     * @param cheapLimit     Products cheaper than this are considered cheap.
     * @param expensiveLimit Products more expensive than this are considered expensive.
     * @return A map containing the cheap, medium and expensive products in separate lists.
     */
    public Map<PriceRange, List<Product>> groupProductsByPriceRange(List<Product> products, BigDecimal cheapLimit, BigDecimal expensiveLimit) {
        // TODO: Group products by price range.
        //
        // - A product is CHEAP if its price is less than cheapLimit.
        // - A product is EXPENSIVE if its price is more than expensiveLimit.
        // - A product is MEDIUM if its price is between cheapLimit and expensiveLimit.
        //
        // Hint: Look carefully at the type of the keys of the map that this method should return. It tells you something about the classifier function.
        // What relation does this have with the classifier function that you use when using a grouping operation?

        return products.stream()
                .collect(Collectors.groupingBy(product -> {
                    BigDecimal price = product.getPrice();
                    if (price.compareTo(cheapLimit) < 0) {
                        return PriceRange.CHEAP;
                    } else if (price.compareTo(expensiveLimit) > 0) {
                        return PriceRange.EXPENSIVE;
                    } else {
                        return PriceRange.MEDIUM;
                    }
                }));
    }

    public static void main(String[] args) {
        AdvancedStreamsExercise03 exercise = new AdvancedStreamsExercise03();
        List<Product> products = ExampleData.getProducts();

        Map<PriceRange, List<Product>> grouped = exercise.groupProductsByPriceRange(
                products,
                new BigDecimal("5.00"),    // cheapLimit
                new BigDecimal("20.00")    // expensiveLimit
        );

        grouped.forEach((range, productList) -> {
            System.out.println(range + " -> " + productList);
        });
    }
}
