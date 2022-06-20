package com.furhalici.garage.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Ticket {
    private final UUID ticketId = UUID.randomUUID();
    private final LocalDateTime ticketCreatedTime = LocalDateTime.now();
}
