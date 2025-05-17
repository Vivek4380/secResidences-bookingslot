package com.example.roombooking.repository;

import com.example.roombooking.entity.Booking;
import com.example.roombooking.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b.slot, COUNT(b) FROM Booking b WHERE b.date = :date GROUP BY b.slot")
    List<Object[]> countByDateAndSlotRaw(@Param("date") LocalDate date);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.date = :date AND b.slot = :slot")
    long countByDateAndSlot(@Param("date") LocalDate date, @Param("slot") Slot slot);

    List<Booking> findByDateAndRoom(LocalDate date, String room);
}