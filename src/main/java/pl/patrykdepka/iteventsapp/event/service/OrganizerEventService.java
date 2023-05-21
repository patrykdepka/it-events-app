package pl.patrykdepka.iteventsapp.event.service;

import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.event.dto.CreateEventDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventDTO;

public interface OrganizerEventService {

    EventDTO createEvent(AppUser currentUser, CreateEventDTO newEventData);
}
