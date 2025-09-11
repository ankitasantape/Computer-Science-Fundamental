package org.example;

public class MotorcycleFactory extends VehicleFactory{

    @Override
    public Vehicle create(String type, String color) {
        if(type.equalsIgnoreCase("Scooter")){
            return new Scooter(color);
        }
        else if(type.equalsIgnoreCase("SportBike")) {
            return new SportBike(color);
        }
        throw new IllegalArgumentException("Unknown Motorcycle type: " + type);
    }
}
