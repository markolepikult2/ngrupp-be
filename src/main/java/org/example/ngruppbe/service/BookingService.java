package org.example.ngruppbe.service;

import org.example.ngruppbe.model.Booking;
import org.example.ngruppbe.model.Customer;
import org.example.ngruppbe.model.Event;
import org.example.ngruppbe.repository.BookingRepository;
import org.example.ngruppbe.repository.EventRepository;
import org.example.ngruppbe.repository.CustomerRepository;
import org.example.ngruppbe.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public BookingDTO addBooking(BookingDTO booking) {
        if (booking.getEvent() == null || booking.getCustomers() == null || booking.getCustomers().isEmpty()) {
            throw new IllegalArgumentException("Event and customers must not be null or empty");
        }
        Customer customer = booking.getCustomers().get(0);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        Example<Customer> customerExample = Example.of(customer, matcher);
        Optional<Customer> foundCustomer  = customerRepository.findOne(customerExample);
        // If the customer does not exist, save it
        if (foundCustomer.isEmpty()) {
            customer = customerRepository.save(customer);
        } else {
            customer = foundCustomer.get();
        }
        Event event = booking.getEvent();
        Optional<Event> foundEvent = eventRepository.findById(event.getId());
        // If the event does not exist, throw error
        if (foundEvent.isEmpty()) {
            throw new IllegalArgumentException("Event does not exist");
        }
        // When Customer is found or saved, we check is there if existing booking for this customer

        ExampleMatcher bookingMatcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnorePaths("bookingTime");

        Booking newBooking = new Booking();
        newBooking.setId(null); // Ensure the ID is null to create a new booking
        newBooking.setEventId(event.getId());
        newBooking.setCustomerId(customer.getId());
        newBooking.setBookingTime(java.time.LocalDateTime.now());

        Example<Booking> bookingExample = Example.of(newBooking, bookingMatcher);

        Optional<Booking> existingBooking = bookingRepository.findOne(bookingExample);
        if (existingBooking.isPresent()) {
            // If a booking already exists, throw an exception
            throw new IllegalArgumentException("Booking already exists for this customer and event");
        }

        // Save the booking
        bookingRepository.save(newBooking);

        // Return the updated BookingDTO
        return new BookingDTO(eventRepository.findById(newBooking.getEventId()).orElse(null), List.of(Objects.requireNonNull(customerRepository.findById(newBooking.getCustomerId()).orElse(null))));
    }
}
