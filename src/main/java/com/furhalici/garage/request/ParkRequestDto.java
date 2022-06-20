package com.furhalici.garage.request;

import com.furhalici.garage.model.CarType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ParkRequestDto {

    @NotEmpty
    private String plaque;
    @NotEmpty
    private String color;
    @NotNull
    private CarType carType;

}
