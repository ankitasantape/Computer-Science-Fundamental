package com.acme.streamslambdas.exercises;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.acme.streamslambdas.exercises.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class BasicStreamsExercise01Test {

    private BasicStreamsExercise01 exercise = new BasicStreamsExercise01();

    @Test
    @DisplayName("Find utensils sorted by name")
    void findUtensilsSortedByName() {
        assertThat(exercise.findUtensilsSortedByName(TestData.getProducts()))
                .describedAs("Exercise 1: Your solution does not return utensils sorted by name.")
                .containsExactly(FORKS, KNIVES, PLATES);
    }
}
