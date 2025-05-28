package org.example.schedulemicroservice.repositories;

import org.example.schedulemicroservice.entities.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    Optional<Timeslot> findByDayOfWeekAndTime(String dayOfWeek, String time);
}
