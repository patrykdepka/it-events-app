package pl.patrykdepka.iteventsapp.appuser.domain;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserFacadeImpl implements CurrentUserFacade {
    private final AppUserRepository appUserRepository;

    public CurrentUserFacadeImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser getCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Optional<AppUser> userOptional = appUserRepository.findByEmail(currentUser.getName());
        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        throw new UsernameNotFoundException(String.format("User with name %s not found", currentUser.getName()));
    }
}
