package pl.patrykdepka.iteventsapp.creator;

import pl.patrykdepka.iteventsapp.appuser.domain.dto.AppUserRegistrationDTO;

public class AppUserRegistrationDTOCreator {

    public static AppUserRegistrationDTO create() {
        return AppUserRegistrationDTO.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .dateOfBirth("1995-10-06")
                .email("jankowalski@example.com")
                .password("tests")
                .confirmPassword("tests")
                .build();
    }
}
