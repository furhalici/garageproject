package com.furhalici.garage.service;

import com.furhalici.garage.exception.ParkBaseCheckedException;
import com.furhalici.garage.model.Car;
import com.furhalici.garage.model.CarType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GarageServiceTest {

    @Autowired
    GarageService garageService;

    @Test
    void parkSlotTest() {
        var car = new Car(CarType.CAR, "p1", "color");
        var truck1 = new Car(CarType.TRUCK, "p2", "color");
        var truck2 = new Car(CarType.TRUCK, "p3", "color");
        var jeep = new Car(CarType.JEEP, "p4", "color");

        garageService.parkCar(truck1);
        garageService.parkCar(truck2);
        garageService.parkCar(jeep);

        assertThrows(ParkBaseCheckedException.class, () -> garageService.parkCar(car));
        garageService.reset();
    }

    @Test
    void parkSlotTestWhenLeavingCar() {
        var car = new Car(CarType.CAR, "p1", "color");
        var truck1 = new Car(CarType.TRUCK, "p2", "color");
        var truck2 = new Car(CarType.TRUCK, "p3", "color");
        var jeep1 = new Car(CarType.JEEP, "p4", "color");
        var jeep2 = new Car(CarType.JEEP, "p5", "color");

        garageService.parkCar(truck1);
        garageService.parkCar(truck2);
        garageService.parkCar(jeep1);

        assertThrows(ParkBaseCheckedException.class, () -> garageService.parkCar(jeep2));
        garageService.leaveCar("p4");
        assertDoesNotThrow(() -> garageService.parkCar(car));
        garageService.reset();
    }


    @Test
    void parkSlotConcurrentTestWhenLeavingCar() {
        var car = new Car(CarType.CAR, "p1", "color");
        var truck1 = new Car(CarType.TRUCK, "p2", "color");
        var truck2 = new Car(CarType.TRUCK, "p3", "color");
        var jeep1 = new Car(CarType.JEEP, "p4", "color");

        garageService.parkCar(truck1);
        garageService.parkCar(truck2);
        garageService.parkCar(jeep1);

        garageService.leaveCar("p4");

        List<CompletableFuture<Boolean>> completableFutures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            completableFutures.add(
                    CompletableFuture.supplyAsync(() -> {
                        try {
                            garageService.parkCar(new Car(CarType.JEEP, UUID.randomUUID().toString(), "color"));
                        } catch (Exception e) {
                            return false;
                        }

                        return true;
                    })
            );
        }

        var parkedCount = completableFutures.stream().map(c -> {
                    try {
                        return c.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .filter(aBoolean -> aBoolean)
                .count();

        assertEquals(1, parkedCount);
        assertEquals(0, garageService.getCurrentCapacity());
        garageService.reset();
    }

}