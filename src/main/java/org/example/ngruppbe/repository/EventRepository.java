package org.example.ngruppbe.repository;

import org.example.ngruppbe.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}

