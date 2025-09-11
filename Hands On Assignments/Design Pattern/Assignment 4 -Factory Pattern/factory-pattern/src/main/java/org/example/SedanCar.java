package org.example;

public class SedanCar implements Vehicle{
    private String color;
    public SedanCar(String color){
        this.color = color;
    }

    @Override
    public void start() {
        System.out.println(color + " Sedan Car started.");
    }

    @Override
    public void stop() {
        System.out.println(color + " Sedan Car stopped.");
    }

    @Override
    public String getColor() {
        return color;
    }
}
