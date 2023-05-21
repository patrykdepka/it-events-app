package pl.patrykdepka.iteventsapp.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.event.dto.CityDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventCardDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventCardDTO> findFirst10UpcomingEvents();

    EventDTO findEvent(Long id);

    List<CityDTO> findAllCities();

    Page<EventCardDTO> findAllUpcomingEvents(LocalDateTime currentDateTime, Pageable pageable);

    Page<EventCardDTO> findUpcomingEventsByCity(String city, LocalDateTime currentDateTime, Pageable pageable);

    Page<EventCardDTO> findAllPastEvents(LocalDateTime currentDateTime, Pageable pageable);

    Page<EventCardDTO> findPastEventsByCity(String city, LocalDateTime currentDateTime, Pageable pageable);

    void addUserToEventParticipantsList(AppUser currentUser, Long id);

    void removeUserFromEventParticipantsList(AppUser currentUser, Long id);

    boolean checkIfCurrentUserIsParticipant(AppUser currentUser, EventDTO event);

    Page<EventCardDTO> findUserEvents(AppUser currentUser, Pageable pageable);

    Page<EventCardDTO> findUserEventsByCity(AppUser currentUser, String city, Pageable pageable);
}
