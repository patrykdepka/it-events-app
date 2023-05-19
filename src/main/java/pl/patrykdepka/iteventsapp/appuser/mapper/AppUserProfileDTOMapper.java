package pl.patrykdepka.iteventsapp.appuser.mapper;

import pl.patrykdepka.iteventsapp.appuser.dto.AppUserProfileDTO;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

import java.time.format.DateTimeFormatter;

public class AppUserProfileDTOMapper {

    private AppUserProfileDTOMapper() {
    }

    public static AppUserProfileDTO mapToAppUserProfileDTO(AppUser user) {
        return AppUserProfileDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .city(user.getCity())
                .bio(user.getBio())
                .build();
    }
}
