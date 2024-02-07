package pl.patrykdepka.iteventsapp.appuser.domain.mapper;

import pl.patrykdepka.iteventsapp.appuser.domain.dto.AppUserProfileDTO;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class AppUserProfileDTOMapper {

    private AppUserProfileDTOMapper() {
    }

    public static AppUserProfileDTO mapToAppUserProfileDTO(AppUser user) {
        return AppUserProfileDTO.builder()
                .profileImageType(user.getProfileImage().getType())
                .profileImageData(Base64.getEncoder().encodeToString(user.getProfileImage().getFileData()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .city(user.getCity())
                .bio(user.getBio())
                .build();
    }
}
