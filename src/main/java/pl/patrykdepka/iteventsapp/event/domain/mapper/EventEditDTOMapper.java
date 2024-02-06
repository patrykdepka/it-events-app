package pl.patrykdepka.iteventsapp.event.domain.mapper;

import pl.patrykdepka.iteventsapp.event.domain.dto.EventEditDTO;
import pl.patrykdepka.iteventsapp.event.domain.AdmissionType;
import pl.patrykdepka.iteventsapp.event.domain.EventType;
import pl.patrykdepka.iteventsapp.event.domain.Event;

import java.util.Base64;

public class EventEditDTOMapper {

    public static EventEditDTO mapToEventEditDTO(Event event) {
        return new EventEditDTO(
                event.getName(),
                event.getEventImage().getFileType(),
                Base64.getEncoder().encodeToString(event.getEventImage().getFileData()),
                null,
                EventType.valueOf(event.getEventType().toString()),
                event.getDateTime().toString(),
                event.getLanguage(),
                AdmissionType.valueOf(event.getAdmission().toString()),
                event.getCity(),
                event.getLocation(),
                event.getAddress(),
                event.getDescription()
        );
    }
}
