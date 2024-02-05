package pl.patrykdepka.iteventsapp.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.event.dto.*;

import java.util.List;

public interface OrganizerEventService {

    EventDTO createEvent(AppUser currentUser, CreateEventDTO newEventData);

    List<CityDTO> findAllCities();

    Page<EventCardDTO> findOrganizerEvents(AppUser currentUser, Pageable page);

    Page<EventCardDTO> findOrganizerEventsByCity(AppUser currentUser, String city, Pageable page);

    EventEditDTO findEventToEdit(AppUser currentUser, Long id);

    void updateEvent(AppUser currentUser, EventEditDTO EventEditDTO);

    Page<ParticipantDTO> findEventParticipants(AppUser currentUser, Long id, Pageable page);

    void removeParticipant(AppUser currentUser, Long eventId, Long participantId);
}
