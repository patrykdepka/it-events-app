package pl.patrykdepka.iteventsapp.event.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykdepka.iteventsapp.event.service.EventService;

import java.time.LocalDateTime;

@Controller
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("events", eventService.findFirst10UpcomingEvents());
        return "index";
    }

    @GetMapping("/events/{id}")
    public String getEvent(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.findEvent(id));
        return "event";
    }

    @GetMapping("/events")
    public String getAllUpcomingEvents(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        model.addAttribute("pathName", "/events");
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "dateTime"));
        model.addAttribute("events", eventService.findAllUpcomingEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/archive/events")
    public String getAllPastEvents(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        model.addAttribute("pathName", "/archive/events");
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", eventService.findAllPastEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }
}
