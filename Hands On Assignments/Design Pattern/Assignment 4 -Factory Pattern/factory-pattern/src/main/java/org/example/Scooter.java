package org.example;

public class Scooter implements Vehicle{
    private String color;
    public Scooter(String color){
        this.color = color;
    }

    @Override
    public void start() {
        System.out.println(color + " Scooter started.");
    }

    @Override
    public void stop() {
        System.out.println(color + " Scooter stopped.");
    }

    @Override
    public String getColor() {
        return color;
    }
}
