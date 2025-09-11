package com.acme.streamslambdas.exercises;

import java.util.function.DoubleBinaryOperator;

public class FunctionalInterfacesExercise03 {

    public enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        MULTIPLY((a, b) -> a * b),
        DIVIDE((a, b) -> a / b);

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        public DoubleBinaryOperator getOperator() {
            return operator;
        }
    }

    /**
     * Exercise 3: Call a DoubleBinaryOperator to perform a calculation.
     *
     * @param a         First input value.
     * @param b         Second input value.
     * @param operation The operation to apply.
     * @return The result of applying the operation to the two input values.
     */
    public double calculate(double a, double b, ArithmeticOperation operation) {
        // TODO: Implement this method; apply the appropriate operation on the values a and b.
        // Note: This is a one-liner. You don't need a switch statement, for example.

        return operation.getOperator().applyAsDouble(a, b);

    }

    public static void main(String[] args) {
        FunctionalInterfacesExercise03 exercise = new FunctionalInterfacesExercise03();

        double x = 20;
        double y = 5;

        System.out.println("Addition: " + exercise.calculate(x, y, ArithmeticOperation.ADD));
        System.out.println("Subtraction: " + exercise.calculate(x, y, ArithmeticOperation.SUBTRACT));
        System.out.println("Multiplication: " + exercise.calculate(x, y, ArithmeticOperation.MULTIPLY));
        System.out.println("Division: " + exercise.calculate(x, y, ArithmeticOperation.DIVIDE));
    }
}