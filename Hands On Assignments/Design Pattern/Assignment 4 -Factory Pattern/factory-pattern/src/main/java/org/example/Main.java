package org.example;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        VehicleFactory carFactory = new CarFactory();
        Vehicle sedan = carFactory.configureVehicle("SedanCar","Blue");
        sedan.start();
        sedan.stop();

        VehicleFactory motorcycleFactory = new MotorcycleFactory();
        Vehicle sportBike = motorcycleFactory.configureVehicle("SportBike", "Red");
        sportBike.start();
        sportBike.stop();
    }
}