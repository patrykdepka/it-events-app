package pl.patrykdepka.iteventsapp.creator;

import pl.patrykdepka.iteventsapp.event.domain.dto.EventEditDTO;
import pl.patrykdepka.iteventsapp.event.domain.AdmissionType;
import pl.patrykdepka.iteventsapp.event.domain.EventType;

import java.io.IOException;
import java.time.LocalDateTime;

public class EventEditDTOCreator {

    public static EventEditDTO create(LocalDateTime localDateTime) throws IOException {
        return EventEditDTO.builder()
                .name("Updated test name")
                .eventImage(EventImageCreator.createNewEventImageFile())
                .eventType(EventType.CONFERENCE)
                .dateTime(localDateTime.plusWeeks(1L).toString())
                .language("Updated test language")
                .admission(AdmissionType.PAID)
                .city("Updated test city")
                .location("Updated test location")
                .address("Updated test address")
                .description("Updated test description")
                .build();
    }
}
