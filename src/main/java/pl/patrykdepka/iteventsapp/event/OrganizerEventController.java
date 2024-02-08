package pl.patrykdepka.iteventsapp.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.iteventsapp.appuser.domain.CurrentUserFacade;
import pl.patrykdepka.iteventsapp.event.domain.OrganizerEventService;
import pl.patrykdepka.iteventsapp.event.domain.dto.CityDTO;
import pl.patrykdepka.iteventsapp.event.domain.dto.CreateEventDTO;
import pl.patrykdepka.iteventsapp.event.domain.dto.EventDTO;
import pl.patrykdepka.iteventsapp.event.domain.dto.EventEditDTO;
import pl.patrykdepka.iteventsapp.event.domain.exception.CityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Controller
class OrganizerEventController {
    private final OrganizerEventService organizerEventService;
    private final CurrentUserFacade currentUserFacade;

    OrganizerEventController(OrganizerEventService organizerEventService, CurrentUserFacade currentUserFacade) {
        this.organizerEventService = organizerEventService;
        this.currentUserFacade = currentUserFacade;
    }

    @GetMapping("/organizer/create_event")
    String showCreateEventForm(Model model) {
        model.addAttribute("newEventData", new CreateEventDTO());
        return "organizer/forms/create-event-form";
    }

    @PostMapping("/organizer/create_event")
    String createEvent(@Valid @ModelAttribute("newEventData") CreateEventDTO newEventData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "organizer/forms/create-event-form";
        } else {
            EventDTO createdEvent = organizerEventService.createEvent(currentUserFacade.getCurrentUser(), newEventData);
            return "redirect:/events/" + createdEvent.getId();
        }
    }

    @GetMapping("/organizer/events")
    String findOrganizerEvents(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        model.addAttribute("cities", organizerEventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", organizerEventService.findOrganizerEvents(currentUserFacade.getCurrentUser(), pageRequest));
        return "organizer/events";
    }

    @GetMapping("/organizer/events/cities/{city}")
    String findOrganizerEventsByCity(
            @PathVariable String city,
            @RequestParam(name = "page", required = false) Integer pageNumber,
            Model model
    ) {
        List<CityDTO> cities = organizerEventService.findAllCities();
        model.addAttribute("cities", cities);
        city = getCity(cities, city);
        model.addAttribute("cityName", city);
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", organizerEventService.findOrganizerEventsByCity(currentUserFacade.getCurrentUser(), city, pageRequest));
        return "organizer/events";
    }

    @GetMapping("/organizer/events/{id}/edit")
    String showEditEventForm(@PathVariable Long id, Model model) {
        model.addAttribute("eventEditData", organizerEventService.findEventToEdit(currentUserFacade.getCurrentUser(), id));
        return "organizer/forms/event-edit-form";
    }

    @PatchMapping("/organizer/events")
    String updateEvent(@Valid @ModelAttribute("eventEditData") EventEditDTO eventEditData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "organizer/forms/event-edit-form";
        } else {
            organizerEventService.updateEvent(currentUserFacade.getCurrentUser(), eventEditData);
            return "redirect:/events/" + eventEditData.getId();
        }
    }

    @GetMapping("/organizer/events/{id}/participants")
    String getEventParticipants(
            @RequestParam(name = "page", required = false) Integer pageNumber,
            @PathVariable Long id,
            Model model
    ) {
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "lastName"));
        model.addAttribute("participants", organizerEventService.findEventParticipants(currentUserFacade.getCurrentUser(), id, pageRequest));
        model.addAttribute("eventId", id);
        return "organizer/participants";
    }

    @GetMapping("/organizer/events/{eventId}/participants/{participantId}/remove")
    String removeParticipant(@PathVariable Long eventId, @PathVariable Long participantId) {
        organizerEventService.removeParticipant(currentUserFacade.getCurrentUser(), eventId, participantId);
        return "redirect:/organizer/events/" + eventId + "/participants";
    }

    private String getCity(List<CityDTO> cities, String city) {
        for (CityDTO cityDTO : cities) {
            if (cityDTO.getNameWithoutPlCharacters().equals(city)) {
                return cityDTO.getDisplayName();
            }
        }

        throw new CityNotFoundException("City with name " + city + " not found");
    }
}
