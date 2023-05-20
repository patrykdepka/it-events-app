package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserProfileEditDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.exception.AppUserNotFoundException;
import pl.patrykdepka.iteventsapp.appuser.mapper.AdminAppUserProfileEditDTOMapper;
import pl.patrykdepka.iteventsapp.appuser.mapper.AdminAppUserTableDTOMapper;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;
import pl.patrykdepka.iteventsapp.appuser.repository.AppUserRepository;
import pl.patrykdepka.iteventsapp.appuser.specification.AppUserSpecification;
import pl.patrykdepka.iteventsapp.profileimage.model.ProfileImage;
import pl.patrykdepka.iteventsapp.profileimage.service.ProfileImageService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AdminAppUserServiceImpl implements AdminAppUserService {
    private final AppUserRepository appUserRepository;
    private final ProfileImageService profileImageService;

    public AdminAppUserServiceImpl(AppUserRepository appUserRepository, ProfileImageService profileImageService) {
        this.appUserRepository = appUserRepository;
        this.profileImageService = profileImageService;
    }

    public Page<AdminAppUserTableDTO> findAllUsers(Pageable pageable) {
        return AdminAppUserTableDTOMapper.mapToAdminAppUserTableDTOs(appUserRepository.findAll(pageable));
    }

    public Page<AdminAppUserTableDTO> findUsersBySearch(String searchQuery, Pageable pageable) {
        searchQuery = searchQuery.toLowerCase();
        String[] searchWords = searchQuery.split(" ");

        if (searchWords.length == 1 && !"".equals(searchWords[0])) {
            return AdminAppUserTableDTOMapper
                    .mapToAdminAppUserTableDTOs(appUserRepository.findAll(AppUserSpecification.bySearch(searchWords[0]), pageable));
        }
        if (searchWords.length == 2) {
            return AdminAppUserTableDTOMapper
                    .mapToAdminAppUserTableDTOs(appUserRepository.findAll(AppUserSpecification.bySearch(searchWords[0], searchWords[1]), pageable));
        }

        return Page.empty();
    }

    public AdminAppUserProfileEditDTO findUserProfileToEdit(Long id) {
        return appUserRepository
                .findById(id)
                .map(AdminAppUserProfileEditDTOMapper::mapToAdminAppUserProfileEditDTO)
                .orElseThrow(() -> new AppUserNotFoundException("User with ID " + id + " not found"));
    }

    @Transactional
    public AdminAppUserProfileEditDTO updateUserProfile(Long id, AdminAppUserProfileEditDTO userProfile) {
        return appUserRepository
                .findById(id)
                .map(target -> setUserProfileFields(userProfile, target))
                .map(AdminAppUserProfileEditDTOMapper::mapToAdminAppUserProfileEditDTO)
                .orElseThrow(() -> new AppUserNotFoundException("User with ID " + id + " not found"));
    }

    private AppUser setUserProfileFields(AdminAppUserProfileEditDTO source, AppUser target) {
        if (source.getProfileImage() != null && !source.getProfileImage().isEmpty()) {
            Optional<ProfileImage> profileImage = profileImageService.updateProfileImage(target, source.getProfileImage());
            profileImage.ifPresent(target::setProfileImage);
        }
        if (source.getFirstName() != null && !source.getFirstName().equals(target.getFirstName())) {
            target.setFirstName(source.getFirstName());
        }
        if (source.getLastName() != null && !source.getLastName().equals(target.getLastName())) {
            target.setLastName(source.getLastName());
        }
        if (source.getDateOfBirth() != null && !source.getDateOfBirth().equals(target.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
            target.setDateOfBirth(LocalDate.parse(source.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if (source.getCity() != null && !source.getCity().equals(target.getCity())) {
            target.setCity(source.getCity());
        }
        if (source.getBio() != null && !source.getBio().equals(target.getBio())) {
            target.setBio(source.getBio());
        }

        return target;
    }
}
