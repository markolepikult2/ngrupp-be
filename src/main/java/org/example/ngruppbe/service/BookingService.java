package org.example.ngruppbe.service;

import org.example.ngruppbe.model.Booking;
import org.example.ngruppbe.model.Customer;
import org.example.ngruppbe.model.Event;
import org.example.ngruppbe.repository.BookingRepository;
import org.example.ngruppbe.repository.EventRepository;
import org.example.ngruppbe.repository.CustomerRepository;
import org.example.ngruppbe.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, EventRepository eventRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
    }

    public BookingDTO getBookingDTOByEventId(Long eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            return null;
        }
        Event event = eventOpt.get();
        List<Booking> bookings = bookingRepository.findByEventId(eventId);
        List<Customer> customers = bookings.stream()
            .map(b -> customerRepository.findById(b.getCustomerId()).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        return new BookingDTO(event, customers);
    }

    public Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
}
