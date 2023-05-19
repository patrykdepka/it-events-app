package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserProfileDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.exception.AppUserNotFoundException;
import pl.patrykdepka.iteventsapp.appuser.mapper.AppUserProfileDTOMapper;
import pl.patrykdepka.iteventsapp.appuser.mapper.AppUserTableDTOMapper;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.appuser.model.Role;
import pl.patrykdepka.iteventsapp.appuser.repository.AppUserRepository;
import pl.patrykdepka.iteventsapp.profileimage.service.ProfileImageService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileImageService profileImageService;

    public AppUserServiceImpl(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            ProfileImageService profileImageService
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileImageService = profileImageService;
    }

    public boolean checkIfUserExists(String email) {
        return appUserRepository.existsByEmail(email.toLowerCase());
    }

    @Transactional
    public void createUser(AppUserRegistrationDTO newUserData) {
        AppUser user = new AppUser();
        user.setProfileImage(profileImageService.createDefaultProfileImage());
        user.setFirstName(newUserData.getFirstName());
        user.setLastName(newUserData.getLastName());
        user.setDateOfBirth(LocalDate.parse(newUserData.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE));
        user.setEmail(newUserData.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(newUserData.getPassword()));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setRoles(List.of(Role.ROLE_USER));
        appUserRepository.save(user);
    }

    public AppUserProfileDTO findUserProfile(AppUser currentUser) {
        return AppUserProfileDTOMapper.mapToAppUserProfileDTO(currentUser);
    }

    public Page<AppUserTableDTO> findAllUsers(Pageable pageable) {
        return AppUserTableDTOMapper.mapToAppUserTableDTOs(appUserRepository.findAll(pageable));
    }

    public AppUserProfileDTO findUserProfileByUserId(Long id) {
        return appUserRepository
                .findById(id)
                .map(AppUserProfileDTOMapper::mapToAppUserProfileDTO)
                .orElseThrow(() -> new AppUserNotFoundException("User with ID " + id + " not found"));
    }
}
