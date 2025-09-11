package org.example;

public class SportBike implements Vehicle{
    private String color;
    public SportBike(String color){
        this.color = color;
    }
    @Override
    public void start() {
        System.out.println(color + " SportBike started.");
    }

    @Override
    public void stop() {
        System.out.println(color + " SportBike stopped.");
    }

    @Override
    public String getColor() {
        return color;
    }
}
