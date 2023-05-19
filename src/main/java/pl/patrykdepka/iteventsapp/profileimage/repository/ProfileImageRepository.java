package pl.patrykdepka.iteventsapp.profileimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.patrykdepka.iteventsapp.profileimage.model.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
