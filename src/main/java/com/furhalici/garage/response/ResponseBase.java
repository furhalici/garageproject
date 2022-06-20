package com.furhalici.garage.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBase {
    private boolean success;
    @Builder.Default
    private LocalDateTime createdTime = LocalDateTime.now();
}
