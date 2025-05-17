package com.example.roombooking.service;

import com.example.roombooking.dto.AvailabilityResponse;
import com.example.roombooking.dto.BookingResponse;
import com.example.roombooking.dto.CreateBookingRequest;
import com.example.roombooking.entity.Booking;
import com.example.roombooking.entity.Slot;
import com.example.roombooking.exception.BookingNotFoundException;
import com.example.roombooking.exception.SlotFullException;
import com.example.roombooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private static final int MAX_BOOKINGS_PER_SLOT = 7;

    private final BookingRepository bookingRepository;

    public AvailabilityResponse getAvailability(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot check availability for past dates");
        }
        Map<Slot, Long> slotCounts = bookingRepository.countByDateAndSlot(date);
        return new AvailabilityResponse(
                MAX_BOOKINGS_PER_SLOT - slotCounts.getOrDefault(Slot.MORNING, 0L),
                MAX_BOOKINGS_PER_SLOT - slotCounts.getOrDefault(Slot.AFTERNOON, 0L),
                MAX_BOOKINGS_PER_SLOT - slotCounts.getOrDefault(Slot.EVENING, 0L)
        );
    }

    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {
        if (request.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot create booking for past dates");
        }

        long currentCount = bookingRepository.countByDateAndSlot(request.getDate(), request.getSlot());
        if (currentCount >= MAX_BOOKINGS_PER_SLOT) {
            throw new SlotFullException("No slots available for selected time");
        }

        Booking booking = new Booking();
        booking.setDate(request.getDate());
        booking.setSlot(request.getSlot());
        booking.setRoom(request.getRoom());
        booking.setCleaned(false);

        booking = bookingRepository.save(booking);
        return toResponse(booking);
    }

    public List<BookingResponse> getBookings(LocalDate date, String room) {
        return bookingRepository.findByDateAndRoom(date, room).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse confirmClean(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        booking.setCleaned(true);
        booking = bookingRepository.save(booking);
        return toResponse(booking);
    }

    // Manual mapping method
    private BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setDate(booking.getDate());
        response.setSlot(booking.getSlot());
        response.setRoom(booking.getRoom());
        response.setCleaned(booking.isCleaned());
        return response;
    }
}