package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailabilityResponse {
    private long morningSlots;
    private long afternoonSlots;
    private long eveningSlots;
}