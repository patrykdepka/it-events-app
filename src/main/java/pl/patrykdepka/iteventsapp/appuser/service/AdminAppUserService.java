package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserPasswordEditDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserProfileEditDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

public interface AdminAppUserService {

    Page<AdminAppUserTableDTO> findAllUsers(Pageable pageable);

    Page<AdminAppUserTableDTO> findUsersBySearch(String searchQuery, Pageable pageable);

    AdminAppUserProfileEditDTO findUserProfileToEdit(Long id);

    AdminAppUserProfileEditDTO updateUserProfile(Long id, AdminAppUserProfileEditDTO userProfile);

    AdminAppUserPasswordEditDTO updateUserPassword(AppUser currentUser, Long id, AdminAppUserPasswordEditDTO newUserPassword);
}
