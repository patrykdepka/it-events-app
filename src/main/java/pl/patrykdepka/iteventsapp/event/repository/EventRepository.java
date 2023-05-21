package pl.patrykdepka.iteventsapp.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.patrykdepka.iteventsapp.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    List<Event> findFirst10EventsByOrderByDateTimeAsc();

    @Query("SELECT DISTINCT e.city FROM Event e")
    List<String> findAllCities();

    @Query("SELECT e FROM Event e WHERE e.dateTime > :currentDateTime")
    Page<Event> findAllUpcomingEvents(@Param("currentDateTime") LocalDateTime currentDateTime, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.city = :city AND e.dateTime > :currentDateTime")
    Page<Event> findUpcomingEventsByCity(@Param("city") String city, @Param("currentDateTime") LocalDateTime currentDateTime, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.dateTime < :currentDateTime")
    Page<Event> findAllPastEvents(@Param("currentDateTime") LocalDateTime currentDateTime, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.city = :city AND e.dateTime < :currentDateTime")
    Page<Event> findPastEventsByCity(@Param("city") String city, @Param("currentDateTime") LocalDateTime currentDateTime, Pageable pageable);
}
