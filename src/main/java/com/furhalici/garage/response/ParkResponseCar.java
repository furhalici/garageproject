package com.furhalici.garage.response;

import com.furhalici.garage.model.CarType;
import com.furhalici.garage.model.Ticket;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParkResponseCar extends ResponseBase {

    private CarType carType;
    private String plaque;
    private Ticket ticket = new Ticket();
    private String color;

}
