package pl.patrykdepka.iteventsapp.event.service;

import org.springframework.stereotype.Service;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.event.dto.CreateEventDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventDTO;
import pl.patrykdepka.iteventsapp.event.mapper.EventDTOMapper;
import pl.patrykdepka.iteventsapp.event.model.Event;
import pl.patrykdepka.iteventsapp.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrganizerEventServiceImpl implements OrganizerEventService {
    private final EventRepository eventRepository;

    public OrganizerEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDTO createEvent(AppUser currentUser, CreateEventDTO newEventData) {
        Event event = new Event();
        event.setName(newEventData.getName());
        event.setEventType(newEventData.getEventType());
        event.setDateTime(LocalDateTime.parse(newEventData.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        event.setLanguage(newEventData.getLanguage());
        event.setAdmission(newEventData.getAdmission());
        event.setCity(newEventData.getCity());
        event.setLocation(newEventData.getLocation());
        event.setAddress(newEventData.getAddress());
        event.setOrganizer(currentUser);
        event.setDescription(newEventData.getDescription());
        return EventDTOMapper.mapToEventDTO(eventRepository.save(event));
    }
}
