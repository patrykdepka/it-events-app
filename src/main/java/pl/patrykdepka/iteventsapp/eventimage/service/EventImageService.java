package pl.patrykdepka.iteventsapp.eventimage.service;

import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.iteventsapp.event.domain.Event;
import pl.patrykdepka.iteventsapp.eventimage.model.EventImage;

import java.util.Optional;

public interface EventImageService {

    EventImage createDefaultEventImage();

    Optional<EventImage> updateEventImage(Event event, MultipartFile newEventImage);
}
