package pl.patrykdepka.iteventsapp.eventimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.patrykdepka.iteventsapp.eventimage.model.EventImage;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
}
