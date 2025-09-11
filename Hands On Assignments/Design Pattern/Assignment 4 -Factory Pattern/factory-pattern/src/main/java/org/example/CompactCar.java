package org.example;

public class CompactCar implements Vehicle{

    private String color;
    public CompactCar(String color){
        this.color = color;
    }

    @Override
    public void start() {
        System.out.println(color + " Compact Car started.");
    }

    @Override
    public void stop() {
        System.out.println("Compact Car stopped.");
    }

    @Override
    public String getColor() {
        return color;
    }
}
