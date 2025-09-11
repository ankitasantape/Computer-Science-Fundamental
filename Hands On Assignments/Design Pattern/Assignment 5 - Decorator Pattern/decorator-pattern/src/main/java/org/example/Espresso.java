package org.example;

public class Espresso implements  Coffee{
    @Override
    public String getDescription() {
        return "Espresso";
    }

    @Override
    public double getCost() {
        return 3.5;
    }
}
