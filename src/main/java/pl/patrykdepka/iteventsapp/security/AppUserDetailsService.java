package pl.patrykdepka.iteventsapp.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;

public interface AppUserDetailsService extends UserDetailsService {

    void updateAppUserDetails(AppUser user);
}
