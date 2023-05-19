package pl.patrykdepka.iteventsapp.appuser.service;

import pl.patrykdepka.iteventsapp.appuser.dto.AppUserProfileDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

public interface AppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO newUserData);

    AppUserProfileDTO findUserProfile(AppUser currentUser);
}
