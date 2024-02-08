package pl.patrykdepka.iteventsapp.creator;

import pl.patrykdepka.iteventsapp.event.domain.dto.CreateEventDTO;
import pl.patrykdepka.iteventsapp.event.domain.AdmissionType;
import pl.patrykdepka.iteventsapp.event.domain.EventType;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class CreateEventDTOCreator {

    public static CreateEventDTO create(LocalDateTime localDateTime) {
        return CreateEventDTO.builder()
                .name("Java Dev Talks #1")
                .eventType(EventType.MEETING)
                .dateTime(localDateTime.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .language("polski")
                .admission(AdmissionType.FREE)
                .city("Rzeszów")
                .location("WSIiZ")
                .address("Sucharskiego 2, 35-225 Rzeszów")
                .description("Spotkanie rzeszowskiej grupy pasjonatów języka Java.")
                .build();
    }
}
