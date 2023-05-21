package pl.patrykdepka.iteventsapp.event.model;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.event.enumeration.AdmissionType;
import pl.patrykdepka.iteventsapp.event.enumeration.EventType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private LocalDateTime dateTime;
    private String language;
    @Enumerated(EnumType.STRING)
    private AdmissionType admission;
    private String city;
    private String location;
    private String address;
    @OneToOne
    @JoinColumn(name = "organizer_id")
    private AppUser organizer;
    private String description;
}
