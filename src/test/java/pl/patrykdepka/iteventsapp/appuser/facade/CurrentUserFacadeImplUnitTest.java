package pl.patrykdepka.iteventsapp.appuser.facade;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.appuser.domain.CurrentUserFacadeImpl;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUserRepository;
import pl.patrykdepka.iteventsapp.creator.AppUserCreator;
import pl.patrykdepka.iteventsapp.creator.AppUserDetailsCreator;
import pl.patrykdepka.iteventsapp.security.AppUserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CurrentUserFacadeImplUnitTest {
    private AppUserRepository appUserRepository;
    private CurrentUserFacadeImpl currentUserFacadeImpl;

    @BeforeEach
    void setUp() {
        appUserRepository = Mockito.mock(AppUserRepository.class);
        currentUserFacadeImpl = new CurrentUserFacadeImpl(appUserRepository);
    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnUserIfIsAuthenticated() {
        // given
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        AppUserDetails userDetails = AppUserDetailsCreator.create(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "tests", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(appUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        // when
        AppUser currentUser = currentUserFacadeImpl.getCurrentUser();
        // then
        assertNotNull(currentUser);
    }

    @Test
    void shouldThrowExceptionIfUserDoesNotExist() {
        // given
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        AppUserDetails userDetails = AppUserDetailsCreator.create(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "tests", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        // then
        assertThatThrownBy(() -> currentUserFacadeImpl.getCurrentUser())
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(String.format("User with name %s not found", user.getEmail()));
    }
}
