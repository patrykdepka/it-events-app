package pl.patrykdepka.iteventsapp.creator;

import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.appuser.domain.Role;

import java.time.LocalDate;
import java.util.List;

import static pl.patrykdepka.iteventsapp.appuser.domain.Role.ROLE_USER;
import static pl.patrykdepka.iteventsapp.creator.ProfileImageCreator.createDefaultProfileImage;

public class AppUserCreator {

    public static AppUser create(Long id, String firstName, String lastName) {
        return AppUser.builder()
                .id(id)
                .profileImage(createDefaultProfileImage(id))
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(LocalDate.of(1995, 10, 6))
                .city(null)
                .bio(null)
                .email(firstName.toLowerCase() + lastName.toLowerCase() + "@example.com")
                .password("{bcrypt}$2a$10$2pZqxIPWTiwqmN.ApjAQsOC6Q/ql4canuxFiZruGYDvZ5RRYDMDr6") // tests
                .enabled(true)
                .accountNonLocked(true)
                .roles(List.of(ROLE_USER))
                .build();
    }

    public static AppUser create(Long id, String firstName, String lastName, Role role) {
        return AppUser.builder()
                .id(id)
                .profileImage(createDefaultProfileImage(id))
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(LocalDate.of(1995, 10, 6))
                .city(null)
                .bio(null)
                .email(firstName.toLowerCase() + lastName.toLowerCase() + "@example.com")
                .password("{bcrypt}$2a$10$2pZqxIPWTiwqmN.ApjAQsOC6Q/ql4canuxFiZruGYDvZ5RRYDMDr6") // tests
                .enabled(true)
                .accountNonLocked(true)
                .roles(List.of(role))
                .build();
    }
}
