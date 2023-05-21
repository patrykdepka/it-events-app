package pl.patrykdepka.iteventsapp.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykdepka.iteventsapp.appuser.facade.CurrentUserFacade;
import pl.patrykdepka.iteventsapp.event.dto.CreateEventDTO;
import pl.patrykdepka.iteventsapp.event.dto.EventDTO;
import pl.patrykdepka.iteventsapp.event.service.OrganizerEventService;

import javax.validation.Valid;

@Controller
public class OrganizerEventController {
    private final OrganizerEventService organizerEventService;
    private final CurrentUserFacade currentUserFacade;

    public OrganizerEventController(OrganizerEventService organizerEventService, CurrentUserFacade currentUserFacade) {
        this.organizerEventService = organizerEventService;
        this.currentUserFacade = currentUserFacade;
    }

    @GetMapping("/organizer/create_event")
    public String showCreateEventForm(Model model) {
        model.addAttribute("newEventData", new CreateEventDTO());
        return "organizer/forms/create-event-form";
    }

    @PostMapping("/organizer/create_event")
    public String createEvent(@Valid @ModelAttribute("newEventData") CreateEventDTO newEventData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "organizer/forms/create-event-form";
        } else {
            EventDTO createdEvent = organizerEventService.createEvent(currentUserFacade.getCurrentUser(), newEventData);
            return "redirect:/events/" + createdEvent.getId();
        }
    }
}
