package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.dto.*;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

public interface AppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO newUserData);

    AppUserProfileDTO findUserProfile(AppUser currentUser);

    Page<AppUserTableDTO> findAllUsers(Pageable pageable);

    Page<AppUserTableDTO> findUsersBySearch(String searchQuery, Pageable pageable);

    AppUserProfileDTO findUserProfileByUserId(Long id);

    AppUserProfileEditDTO findUserProfileToEdit(AppUser currentUser);

    AppUserProfileEditDTO updateUserProfile(AppUser currentUser, AppUserProfileEditDTO userProfile);

    AppUserPasswordEditDTO updateUserPassword(AppUser currentUser, AppUserPasswordEditDTO newUserPassword);
}
