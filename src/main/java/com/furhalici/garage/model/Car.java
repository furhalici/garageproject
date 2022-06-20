package com.furhalici.garage.model;

import com.furhalici.garage.request.ParkRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class Car {
    private final CarType carType;
    private final String plaque;
    private final Ticket ticket = new Ticket();
    private final String color;
    private LocalDateTime entranceTime;

    public int getCapacity() {
        return carType.getCapacity();
    }

    public void setEntranceTime() {
        entranceTime = LocalDateTime.now();
    }

    public static Car createCar(ParkRequestDto parkRequestDto) {
        return new Car(parkRequestDto.getCarType(), parkRequestDto.getPlaque(), parkRequestDto.getColor());
    }

}
