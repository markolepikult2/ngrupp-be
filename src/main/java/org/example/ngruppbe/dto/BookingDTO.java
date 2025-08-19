package org.example.ngruppbe.dto;

import org.example.ngruppbe.model.Event;
import org.example.ngruppbe.model.Customer;
import java.util.List;
import lombok.Data;

@Data
public class BookingDTO {
    private Event event;
    private List<Customer> customers;

    public BookingDTO() {
        // Default constructor for frameworks
    }

    public BookingDTO(Event event, List<Customer> customers) {
        this.event = event;
        this.customers = customers;
    }
}
