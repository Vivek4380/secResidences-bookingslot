package com.example.roombooking.controller;

import com.example.roombooking.dto.AvailabilityResponse;
import com.example.roombooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotController {
    private final BookingService bookingService;

    @GetMapping("/availability")
    public AvailabilityResponse getAvailability(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return bookingService.getAvailability(date);
    }
}