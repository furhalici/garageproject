package com.furhalici.garage.service;

import com.furhalici.garage.exception.CarNotFoundException;
import com.furhalici.garage.exception.ParkSlotNotFoundException;
import com.furhalici.garage.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class GarageService {
    private static final int CAPACITY = 10;
    private final LinkedList<Car> slots = new LinkedList<>();
    private final ReentrantLock reentrantLock = new ReentrantLock();

    private final AtomicInteger currentCapacity = new AtomicInteger(CAPACITY);

    public void parkCar(Car car) {
        if (car.getCapacity() > currentCapacity.get()) {
            throw new ParkSlotNotFoundException();
        }
        try {
            reentrantLock.lock();
            slots.add(car);
            currentCapacity.addAndGet(-1 * car.getCapacity());
            log.info("Park Car (type={}, plaque={})", car.getCarType().name(), car.getPlaque());
        } finally {
            reentrantLock.unlock();
        }

    }

    public void leaveCar(String plaque) {
        try {
            reentrantLock.lock();
            var car = slots.stream()
                    .filter(c -> plaque.equals(c.getPlaque()))
                    .findFirst()
                    .orElseThrow(CarNotFoundException::new);

            slots.remove(car);
            currentCapacity.addAndGet(car.getCapacity());
            log.info("Leave Car (type={}, plaque={})", car.getCarType().name(), car.getPlaque());
        } finally {
            reentrantLock.unlock();
        }
    }

    public List<Car> parkedCars() {
        return (LinkedList<Car>) slots.clone();
    }

    public int getCurrentCapacity() {
        return currentCapacity.get();
    }

    public void reset() {
        try {
            reentrantLock.lock();
            slots.clear();
            currentCapacity.set(CAPACITY);
        } finally {
            reentrantLock.unlock();
        }
    }
}
