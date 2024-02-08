package pl.patrykdepka.iteventsapp.event.service;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.event.dto.CityDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventCardDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventDTO;
import pl.patrykdepka.iteventsapp.event.exception.EventNotFoundException;
import pl.patrykdepka.iteventsapp.event.mapper.EventCardDTOMapper;
import pl.patrykdepka.iteventsapp.event.mapper.EventDTOMapper;
import pl.patrykdepka.iteventsapp.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventCardDTO> findFirst10UpcomingEvents() {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findFirst10EventsByOrderByDateTimeAsc());
    }

    public EventDTO findEvent(Long id) {
        return eventRepository
                .findById(id)
                .map(EventDTOMapper::mapToEventDTO)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    public List<CityDTO> findAllCities() {
        List<String> cities = eventRepository.findAllCities();
        List<CityDTO> cityDTOs = new ArrayList<>();
        for (String city : cities) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setNameWithoutPlCharacters(getCityNameWithoutPlCharacters(city));
            cityDTO.setDisplayName(city);
            cityDTOs.add(cityDTO);
        }
        return cityDTOs;
    }

    public Page<EventCardDTO> findAllUpcomingEvents(LocalDateTime currentDateTime, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findAllUpcomingEvents(currentDateTime, page));
    }

    public Page<EventCardDTO> findUpcomingEventsByCity(String city, LocalDateTime currentDateTime, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findUpcomingEventsByCity(city, currentDateTime, page));
    }

    public Page<EventCardDTO> findAllPastEvents(LocalDateTime currentDateTime, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findAllPastEvents(currentDateTime, page));
    }

    public Page<EventCardDTO> findPastEventsByCity(String city, LocalDateTime currentDateTime, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findPastEventsByCity(city, currentDateTime, page));
    }

    @Transactional
    public void addUserToEventParticipantsList(AppUser currentUser, Long id) {
        eventRepository
                .findById(id)
                .map(event -> event.addParticipant(currentUser))
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    @Transactional
    public void removeUserFromEventParticipantsList(AppUser currentUser, Long id) {
        eventRepository
                .findById(id)
                .map(event -> event.removeParticipant(currentUser))
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    public boolean checkIfCurrentUserIsParticipant(AppUser currentUser, EventDTO event) {
        return event.checkIfCurrentUserIsParticipant(currentUser);
    }

    public Page<EventCardDTO> findUserEvents(AppUser user, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findUserEvents(user, page));
    }

    public Page<EventCardDTO> findUserEventsByCity(AppUser user, String city, Pageable page) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findUserEventsByCity(user, city, page));
    }

    private String getCityNameWithoutPlCharacters(String city) {
        city = city.toLowerCase();
        city = city.replace("\\s", "-");
        city = StringUtils.stripAccents(city);
        return city;
    }
}
