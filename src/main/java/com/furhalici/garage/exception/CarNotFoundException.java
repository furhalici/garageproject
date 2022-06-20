package com.furhalici.garage.exception;

public class CarNotFoundException extends ParkBaseCheckedException {

    public CarNotFoundException() {
        super("Car not found");
    }

}
