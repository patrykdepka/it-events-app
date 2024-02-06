package pl.patrykdepka.iteventsapp.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykdepka.iteventsapp.appuser.domain.CurrentUserFacade;
import pl.patrykdepka.iteventsapp.event.domain.dto.CityDTO;
import pl.patrykdepka.iteventsapp.event.domain.dto.EventDTO;
import pl.patrykdepka.iteventsapp.event.domain.exception.CityNotFoundException;
import pl.patrykdepka.iteventsapp.event.domain.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
class EventController {
    private final EventService eventService;
    private final CurrentUserFacade currentUserFacade;

    EventController(EventService eventService, CurrentUserFacade currentUserFacade) {
        this.eventService = eventService;
        this.currentUserFacade = currentUserFacade;
    }

    @GetMapping("/")
    String showMainPage(Model model) {
        model.addAttribute("events", eventService.findFirst10UpcomingEvents());
        return "index";
    }

    @GetMapping("/events/{id}")
    String getEvent(@PathVariable Long id, Model model) {
        EventDTO event = eventService.findEvent(id, currentUserFacade.getCurrentUser());
        model.addAttribute("event", event);
        return "event";
    }

    @GetMapping("/events")
    String getAllUpcomingEvents(
            @RequestParam(name = "page", required = false) Integer pageNumber,
            Model model
    ) {
        model.addAttribute("pathName", "/events");
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "dateTime"));
        model.addAttribute("events", eventService.findAllUpcomingEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/events/cities/{city}")
    String getUpcomingEventsByCity(
            @PathVariable String city,
            @RequestParam(name = "page", required = false) Integer pageNumber,
            Model model
    ) {
        model.addAttribute("pathName", "/events");
        List<CityDTO> cities = eventService.findAllCities();
        model.addAttribute("cities", cities);
        city = getCity(cities, city);
        model.addAttribute("cityName", city);
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "dateTime"));
        model.addAttribute("events", eventService.findUpcomingEventsByCity(city, LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/archive/events")
    String getAllPastEvents(
            @RequestParam(name = "page", required = false) Integer pageNumber,
            Model model
    ) {
        model.addAttribute("pathName", "/archive/events");
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", eventService.findAllPastEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/archive/events/cities/{city}")
    String getPastEventsByCity(
            @PathVariable String city,
            @RequestParam(name = "page", required = false) Integer pageNumber,
            Model model
    ) {
        model.addAttribute("pathName", "/archive/events");
        List<CityDTO> cities = eventService.findAllCities();
        model.addAttribute("cities", cities);
        city = getCity(cities, city);
        model.addAttribute("cityName", city);
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", eventService.findPastEventsByCity(city, LocalDateTime.now(), pageRequest));
        return "events";
    }

    @PatchMapping("/events/{id}/join")
    String joinEvent(@PathVariable Long id) {
        eventService.addUserToEventParticipantsList(currentUserFacade.getCurrentUser(), id);
        return "redirect:/events/" + id;
    }

    @PatchMapping("/events/{id}/leave")
    String leaveEvent(@PathVariable Long id) {
        eventService.removeUserFromEventParticipantsList(currentUserFacade.getCurrentUser(), id);
        return "redirect:/events/" + id;
    }

    @GetMapping("/events/my_events")
    String getUserEvents(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        model.addAttribute("pathName", "/events/my_events");
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", eventService.findUserEvents(currentUserFacade.getCurrentUser(), pageRequest));
        return "events";
    }

    @GetMapping("/events/my_events/cities/{city}")
    String getUserEventsByCity(
            @PathVariable String city,
            @RequestParam(name = "page", required = false) Integer pageNumber,
            Model model
    ) {
        model.addAttribute("pathName", "/events/my_events");
        List<CityDTO> cities = eventService.findAllCities();
        model.addAttribute("cities", cities);
        city = getCity(cities, city);
        model.addAttribute("cityName", city);
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", eventService.findUserEventsByCity(currentUserFacade.getCurrentUser(), city, pageRequest));
        return "events";
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
