package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserProfileDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

public interface AppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO newUserData);

    AppUserProfileDTO findUserProfile(AppUser currentUser);

    Page<AppUserTableDTO> findAllUsers(Pageable pageable);

    Page<AppUserTableDTO> findUsersBySearch(String searchQuery, Pageable pageable);

    AppUserProfileDTO findUserProfileByUserId(Long id);
}
