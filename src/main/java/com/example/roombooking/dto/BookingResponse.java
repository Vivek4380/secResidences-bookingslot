package com.example.roombooking.dto;

import com.example.roombooking.entity.Slot;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {
    private Long id;
    private LocalDate date;
    private Slot slot;
    private String room;
    private boolean cleaned;
}