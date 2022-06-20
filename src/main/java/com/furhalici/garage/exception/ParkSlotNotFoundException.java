package com.furhalici.garage.exception;

public class ParkSlotNotFoundException extends ParkBaseCheckedException {

    public ParkSlotNotFoundException() {
        super("Park slot not found");
    }

}
