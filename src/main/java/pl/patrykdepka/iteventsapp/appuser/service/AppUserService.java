package pl.patrykdepka.iteventsapp.appuser.service;

import pl.patrykdepka.iteventsapp.appuser.dto.AppUserRegistrationDTO;

public interface AppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO newUserData);
}
