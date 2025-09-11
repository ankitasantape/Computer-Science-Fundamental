package org.example;

public abstract class VehicleFactory {
    public abstract Vehicle create(String type, String color);

    public Vehicle configureVehicle(String type, String color) {
        Vehicle vehicle = create(type, color);
        System.out.println("Created a " + color + " " + type);
//        vehicle.start();
//        vehicle.stop();
        return vehicle;
    }
}
