package com.example.roombooking.controller;

import com.example.roombooking.dto.BookingResponse;
import com.example.roombooking.dto.CreateBookingRequest;
import com.example.roombooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping
    public List<BookingResponse> getBookings(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String room) {
        return bookingService.getBookings(date, room);
    }

    @PostMapping("/{id}/confirm-clean")
    public BookingResponse confirmClean(@PathVariable Long id) {
        return bookingService.confirmClean(id);
    }
}