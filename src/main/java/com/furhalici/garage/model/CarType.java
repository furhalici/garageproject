package com.furhalici.garage.model;

import lombok.Getter;

public enum CarType {
    CAR(1),
    JEEP(2),
    TRUCK(4);

    @Getter
    private final int capacity;

    CarType(int capacity) {
        this.capacity = capacity;
    }
}
