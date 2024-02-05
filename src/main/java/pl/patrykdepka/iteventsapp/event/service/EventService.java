package pl.patrykdepka.iteventsapp.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.event.dto.CityDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventCardDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventCardDTO> findFirst10UpcomingEvents();

    EventDTO findEvent(Long id);

    List<CityDTO> findAllCities();

    Page<EventCardDTO> findAllUpcomingEvents(LocalDateTime currentDateTime, Pageable page);

    Page<EventCardDTO> findUpcomingEventsByCity(String city, LocalDateTime currentDateTime, Pageable page);

    Page<EventCardDTO> findAllPastEvents(LocalDateTime currentDateTime, Pageable page);

    Page<EventCardDTO> findPastEventsByCity(String city, LocalDateTime currentDateTime, Pageable page);

    void addUserToEventParticipantsList(AppUser currentUser, Long id);

    void removeUserFromEventParticipantsList(AppUser currentUser, Long id);

    boolean checkIfCurrentUserIsParticipant(AppUser currentUser, EventDTO event);

    Page<EventCardDTO> findUserEvents(AppUser currentUser, Pageable page);

    Page<EventCardDTO> findUserEventsByCity(AppUser currentUser, String city, Pageable page);
}
