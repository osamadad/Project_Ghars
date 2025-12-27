package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer> {

    List<Event> findAllByDateGreaterThanEqual(LocalDate date);

    Event findEventById(Integer id);

    List<Event> findAllByDateGreaterThanEqualOrderByDateAscStartTimeAsc(LocalDate date);

    List<Event> findAllByLocationAndDateGreaterThanEqualOrderByDateAscStartTimeAsc(String location, LocalDate date);
}
