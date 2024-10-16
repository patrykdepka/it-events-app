package pl.patrykdepka.iteventsapp.event.domain.mapper;

import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.event.domain.dto.EventDTO;
import pl.patrykdepka.iteventsapp.event.domain.Event;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class EventDTOMapper {

    public static EventDTO mapToEventDTO(Event event, AppUser currentUser) {
        return new EventDTO.EventDTOBuilder()
                .id(event.getId())
                .name(event.getName())
                .contentType(event.getEventImage().getContentType())
                .imageData(Base64.getEncoder().encodeToString(event.getEventImage().getFileData()))
                .eventType(event.getEventType().getDisplayName())
                .date(event.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .hour(event.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .language(event.getLanguage())
                .admission(event.getAdmission().getDisplayName())
                .city(event.getCity())
                .location(event.getLocation())
                .address(event.getAddress())
                .organizerId(event.getOrganizer().getId())
                .organizerImageType(event.getOrganizer().getProfileImage().getType())
                .organizerImageData(Base64.getEncoder().encodeToString(event.getOrganizer().getProfileImage().getFileData()))
                .organizerName(event.getOrganizer().getFirstName() + " " + event.getOrganizer().getLastName())
                .description(event.getDescription())
                .currentUserIsParticipant(event.checkIfUserIsParticipant(currentUser))
                .build();
    }
}
