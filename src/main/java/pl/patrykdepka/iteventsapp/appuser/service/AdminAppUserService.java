package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.dto.*;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

public interface AdminAppUserService {

    Page<AdminAppUserTableDTO> findAllUsers(Pageable pageable);

    Page<AdminAppUserTableDTO> findUsersBySearch(String searchQuery, Pageable pageable);

    AdminAppUserAccountEditDTO findUserAccountToEdit(Long id);

    AdminAppUserAccountEditDTO updateUserAccount(Long id, AdminAppUserAccountEditDTO userAccount);

    AdminAppUserProfileEditDTO findUserProfileToEdit(Long id);

    AdminAppUserProfileEditDTO updateUserProfile(Long id, AdminAppUserProfileEditDTO userProfile);

    AdminAppUserPasswordEditDTO updateUserPassword(AppUser currentUser, Long id, AdminAppUserPasswordEditDTO newUserPassword);

    void deleteUser(AppUser currentUser, AdminDeleteAppUserDTO deleteUserData);
}
