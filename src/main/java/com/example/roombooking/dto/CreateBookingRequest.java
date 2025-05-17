package com.example.roombooking.dto;

import com.example.roombooking.entity.Slot;
import com.example.roombooking.validation.FutureDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateBookingRequest {
    @NotNull
    @FutureDate
    private LocalDate date;

    @NotNull
    private Slot slot;

    @NotBlank
    private String room;
}