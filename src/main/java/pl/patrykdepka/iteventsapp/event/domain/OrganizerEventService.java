package pl.patrykdepka.iteventsapp.event.domain;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.event.domain.dto.*;
import pl.patrykdepka.iteventsapp.event.domain.exception.EventNotFoundException;
import pl.patrykdepka.iteventsapp.event.domain.mapper.EventCardDTOMapper;
import pl.patrykdepka.iteventsapp.event.domain.mapper.EventDTOMapper;
import pl.patrykdepka.iteventsapp.event.domain.mapper.EventEditDTOMapper;
import pl.patrykdepka.iteventsapp.event.domain.mapper.ParticipantDTOMapper;
import pl.patrykdepka.iteventsapp.image.domain.ImageService;
import pl.patrykdepka.iteventsapp.image.domain.ImageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizerEventService {
    private final EventRepository eventRepository;
    private final ImageService imageService;

    public OrganizerEventService(EventRepository eventRepository, ImageService imageService) {
        this.eventRepository = eventRepository;
        this.imageService = imageService;
    }

    public EventDTO createEvent(AppUser currentUser, CreateEventDTO newEventData) {
        Event event = new Event();
        event.setName(newEventData.getName());
        event.setEventImage(imageService.createDefaultImage(ImageService.DEFAULT_EVENT_IMAGE_NAME, ImageType.EVENT_IMAGE));
        event.setEventType(newEventData.getEventType());
        event.setDateTime(LocalDateTime.parse(newEventData.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        event.setLanguage(newEventData.getLanguage());
        event.setAdmission(newEventData.getAdmission());
        event.setCity(newEventData.getCity());
        event.setLocation(newEventData.getLocation());
        event.setAddress(newEventData.getAddress());
        event.setOrganizer(currentUser);
        event.setDescription(newEventData.getDescription());
        Event createdEvent = eventRepository.save(event);
        return EventDTOMapper.mapToEventDTO(createdEvent, currentUser);
    }

    public List<CityDTO> findAllCities() {
        List<String> cities = eventRepository.findAllCities();
        List<CityDTO> cityDTOs = new ArrayList<>();
        for (String city : cities) {
            CityDTO cityDTO = new CityDTO(
                    getCityNameWithoutPlCharacters(city),
                    city
            );
            cityDTOs.add(cityDTO);
        }
        return cityDTOs;
    }

    public Page<EventCardDTO> findOrganizerEvents(AppUser currentUser, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findOrganizerEvents(currentUser, page));
    }

    public Page<EventCardDTO> findOrganizerEventsByCity(AppUser currentUser, String city, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findOrganizerEventsByCity(currentUser, city, page));
    }

    public EventEditDTO findEventToEdit(AppUser currentUser, Long id) {
        return EventEditDTOMapper.mapToEventEditDTO(returnEventIfCurrentUserIsOrganizer(currentUser, id));
    }

    @Transactional
    public void updateEvent(Long id, EventEditDTO eventEditData) {
        eventRepository
                .findById(id)
                .ifPresentOrElse(
                        event -> {
                            setEventFields(eventEditData, event);
                        },
                        () -> {
                            throw new EventNotFoundException("Event with ID " + id + " not found");
                        });
    }

    public Page<ParticipantDTO> findEventParticipants(AppUser currentUser, Long id, Pageable page) {
        Event event = returnEventIfCurrentUserIsOrganizer(currentUser, id);
        return ParticipantDTOMapper.mapToParticipantDTOs(event.getParticipants(), page);
    }

    @Transactional
    public void removeParticipant(AppUser currentUser, Long eventId, Long participantId) {
        Event event = returnEventIfCurrentUserIsOrganizer(currentUser, eventId);
        AppUser user = event.getParticipants().stream().filter(participant -> participant.getId() == participantId).findFirst().get();
        event.removeParticipant(user);
    }

    private String getCityNameWithoutPlCharacters(String city) {
        city = city.toLowerCase();
        city = city.replace("\\s", "-");
        city = StringUtils.stripAccents(city);
        return city;
    }

    private Event returnEventIfCurrentUserIsOrganizer(AppUser currentUser, Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (!currentUser.equals(event.getOrganizer())) {
                throw new AccessDeniedException("Access is denied");
            }

            return event;
        }

        throw new EventNotFoundException("Event with ID " + id + " not found");
    }

    private Event setEventFields(EventEditDTO source, Event target) {
        if (source.getName() != null && !source.getName().equals(target.getName())) {
            target.setName(source.getName());
        }
        if (!source.getEventImage().isEmpty()) {
            imageService.updateImage(target.getEventImage().getId(), source.getEventImage());
        }
        if (source.getDateTime() != null && !source.getDateTime().equals(target.getDateTime().toString())) {
            target.setDateTime(LocalDateTime.parse(source.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        if (source.getEventType() != null && source.getEventType() != target.getEventType()) {
            target.setEventType(source.getEventType());
        }
        if (source.getLanguage() != null && !source.getLanguage().equals(target.getLanguage())) {
            target.setLanguage(source.getLanguage());
        }
        if (source.getAdmission() != null && source.getAdmission() != target.getAdmission()) {
            target.setAdmission(source.getAdmission());
        }
        if (source.getCity() != null && !source.getCity().equals(target.getCity())) {
            target.setCity(source.getCity());
        }
        if (source.getLocation() != null && !source.getLocation().equals(target.getLocation())) {
            target.setLocation(source.getLocation());
        }
        if (source.getAddress() != null && !source.getAddress().equals(target.getAddress())) {
            target.setAddress(source.getAddress());
        }
        if (source.getDescription() != null && !source.getDescription().equals(target.getDescription())) {
            target.setDescription(source.getDescription());
        }

        return target;
    }
}
