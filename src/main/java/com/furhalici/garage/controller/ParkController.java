package com.furhalici.garage.controller;

import com.furhalici.garage.model.Car;
import com.furhalici.garage.request.ParkRequestDto;
import com.furhalici.garage.response.ParkResponseCar;
import com.furhalici.garage.service.GarageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("v1/park")
@RequiredArgsConstructor
public class ParkController {

    private final GarageService garageService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ParkResponseCar>> parkedCars() {
        return ResponseEntity.ok()
                .body(
                        garageService.parkedCars()
                                .stream()
                                .map(v -> modelMapper.map(v, ParkResponseCar.class))
                                .collect(Collectors.toList())
                );
    }

    @PostMapping
    public ResponseEntity<ParkResponseCar> parkCar(@Valid @RequestBody ParkRequestDto parkRequestDto) {
        garageService.parkCar(Car.createCar(parkRequestDto));

        var response = modelMapper.map(parkRequestDto, ParkResponseCar.class);
        response.setSuccess(true);
        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("plaque/{plaque}")
    public ResponseEntity<Void> leaveCar(@PathVariable String plaque) {
        garageService.leaveCar(plaque);
        return ResponseEntity.noContent().build();
    }

}
