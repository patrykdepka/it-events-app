package pl.patrykdepka.iteventsapp.eventimage.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pl.patrykdepka.iteventsapp.eventimage.exception.DefaultEventImageNotFoundException;
import pl.patrykdepka.iteventsapp.eventimage.model.EventImage;
import pl.patrykdepka.iteventsapp.eventimage.repository.EventImageRepository;

import java.io.IOException;
import java.io.InputStream;

@Service
public class EventImageServiceImpl implements EventImageService {
    private final EventImageRepository eventImageRepository;

    public EventImageServiceImpl(EventImageRepository eventImageRepository) {
        this.eventImageRepository = eventImageRepository;
    }

    public EventImage createDefaultEventImage() {
        ClassPathResource resource = new ClassPathResource("static/images/default_event_image.png");
        try (InputStream defaultEventImage = resource.getInputStream()) {
            EventImage eventImage = new EventImage();
            eventImage.setFileName(resource.getFilename());
            eventImage.setFileType("image/png");
            eventImage.setFileData(defaultEventImage.readAllBytes());
            return eventImageRepository.save(eventImage);
        } catch (IOException e) {
            throw new DefaultEventImageNotFoundException("File " + resource.getPath() + " not found");
        }
    }
}
