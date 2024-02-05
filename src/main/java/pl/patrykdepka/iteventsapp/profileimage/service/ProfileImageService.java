package pl.patrykdepka.iteventsapp.profileimage.service;

import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.profileimage.model.ProfileImage;

import java.util.Optional;

public interface ProfileImageService {

    ProfileImage createDefaultProfileImage();

    Optional<ProfileImage> updateProfileImage(AppUser user, MultipartFile newProfileImage);
}
