package pl.patrykdepka.iteventsapp.event.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykdepka.iteventsapp.event.dto.CityDTO;
import pl.patrykdepka.iteventsapp.event.exception.CityNotFoundException;
import pl.patrykdepka.iteventsapp.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

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
    public String getAllUpcomingEvents(@RequestParam(name = "page", required = false) Integer pageNumber,
                                       Model model) {
        model.addAttribute("pathName", "/events");
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "dateTime"));
        model.addAttribute("events", eventService.findAllUpcomingEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/events/cities/{city}")
    public String getUpcomingEventsByCity(@PathVariable String city,
                                          @RequestParam(name = "page", required = false) Integer pageNumber,
                                          Model model) {
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
    public String getAllPastEvents(@RequestParam(name = "page", required = false) Integer pageNumber,
                                   Model model) {
        model.addAttribute("pathName", "/archive/events");
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "dateTime"));
        model.addAttribute("events", eventService.findAllPastEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/archive/events/cities/{city}")
    public String getPastEventsByCity(@PathVariable String city,
                                      @RequestParam(name = "page", required = false) Integer pageNumber,
                                      Model model) {
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

    private String getCity(List<CityDTO> cities, String city) {
        for (CityDTO cityDTO : cities) {
            if (cityDTO.getNameWithoutPlCharacters().equals(city)) {
                return cityDTO.getDisplayName();
            }
        }

        throw new CityNotFoundException("City with name " + city + " not found");
    }
}
