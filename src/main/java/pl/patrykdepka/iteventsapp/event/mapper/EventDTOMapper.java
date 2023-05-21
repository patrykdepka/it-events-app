package pl.patrykdepka.iteventsapp.event.mapper;

import pl.patrykdepka.iteventsapp.event.dto.EventDTO;
import pl.patrykdepka.iteventsapp.event.model.Event;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class EventDTOMapper {

    public static EventDTO mapToEventDTO(Event event) {
        return new EventDTO.EventDTOBuilder()
                .id(event.getId())
                .name(event.getName())
                .eventType(event.getEventType().getDisplayName())
                .date(event.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .hour(event.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .language(event.getLanguage())
                .admission(event.getAdmission().getDisplayName())
                .city(event.getCity())
                .location(event.getLocation())
                .address(event.getAddress())
                .organizerId(event.getOrganizer().getId())
                .organizerImageType(event.getOrganizer().getProfileImage().getFileType())
                .organizerImageData(Base64.getEncoder().encodeToString(event.getOrganizer().getProfileImage().getFileData()))
                .organizerName(event.getOrganizer().getFirstName() + " " + event.getOrganizer().getLastName())
                .description(event.getDescription())
                .build();
    }
}
