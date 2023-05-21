package pl.patrykdepka.iteventsapp.event.model;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.event.enumeration.AdmissionType;
import pl.patrykdepka.iteventsapp.event.enumeration.EventType;
import pl.patrykdepka.iteventsapp.eventimage.model.EventImage;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_event_image",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_image_id", referencedColumnName = "id")
    )
    private EventImage eventImage;
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
    @OneToMany
    @JoinTable(
            name = "event_app_user",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    )
    private List<AppUser> participants = new ArrayList<>();

    public boolean addParticipant(AppUser user) {
        participants.add(user);
        return true;
    }

    public boolean removeParticipant(AppUser user) {
        participants.remove(user);
        return true;
    }
}
