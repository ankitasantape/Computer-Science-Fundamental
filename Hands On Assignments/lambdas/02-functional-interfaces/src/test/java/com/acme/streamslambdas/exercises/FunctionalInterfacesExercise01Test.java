package com.acme.streamslambdas.exercises;

import com.acme.streamslambdas.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static com.acme.streamslambdas.Category.FOOD;
import static com.acme.streamslambdas.exercises.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class FunctionalInterfacesExercise01Test {

    private FunctionalInterfacesExercise01 exercise = new FunctionalInterfacesExercise01();

    @Test
    @DisplayName("Filter products")
    void filterProducts() {
        assertThat(exercise.filterProducts(TestData.getProducts(), (Predicate<Product>) product -> product.getCategory() == FOOD))
                .describedAs("Exercise 1: When searching for FOOD products, only those should be returned.")
                .containsExactly(APPLES, SPAGHETTI, COFFEE);

        assertThat(exercise.filterProducts(TestData.getProducts(), (Predicate<Product>) product -> product.getCategory() != FOOD))
                .describedAs("Exercise 2: When searching for non-FOOD products, only those should be returned.")
                .containsExactly(PENCILS, PLATES, DETERGENT);
    }
}