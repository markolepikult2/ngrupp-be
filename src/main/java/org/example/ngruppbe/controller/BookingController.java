package org.example.ngruppbe.controller;

import org.example.ngruppbe.dto.BookingDTO;
import org.example.ngruppbe.model.Booking;
import org.example.ngruppbe.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/booking")
@Validated
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<BookingDTO> getBookingDTOByEventId(@PathVariable("eventId") Long eventId) {
        BookingDTO bookingDTO = bookingService.getBookingDTOByEventId(eventId);
        if (bookingDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingDTO);
    }

    @PostMapping
    public ResponseEntity<Booking> addBooking(@Valid @RequestBody Booking booking) {
        Booking savedBooking = bookingService.addBooking(booking);
        return ResponseEntity.ok(savedBooking);
    }
}
