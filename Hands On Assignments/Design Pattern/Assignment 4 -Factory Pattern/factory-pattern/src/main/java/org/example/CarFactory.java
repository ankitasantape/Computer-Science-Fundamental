package org.example;

public class CarFactory extends VehicleFactory{
    @Override
    public Vehicle create(String type, String color) {
        if ( type.equalsIgnoreCase("CompactCar")){
            return new CompactCar(color);
        } else if(type.equalsIgnoreCase("SedanCar")){
            return new SedanCar(color);
        }
        throw new IllegalArgumentException("Unknown Car type: " + type);
    }
}

