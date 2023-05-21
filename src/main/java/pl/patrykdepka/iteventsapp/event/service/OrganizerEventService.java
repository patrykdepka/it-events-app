package pl.patrykdepka.iteventsapp.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.event.dto.CityDTO;
import pl.patrykdepka.iteventsapp.event.dto.CreateEventDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventCardDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventDTO;

import java.util.List;

public interface OrganizerEventService {

    EventDTO createEvent(AppUser currentUser, CreateEventDTO newEventData);

    List<CityDTO> findAllCities();

    Page<EventCardDTO> findOrganizerEvents(AppUser currentUser, Pageable pageable);

    Page<EventCardDTO> findOrganizerEventsByCity(AppUser currentUser, String city, Pageable pageable);
}
