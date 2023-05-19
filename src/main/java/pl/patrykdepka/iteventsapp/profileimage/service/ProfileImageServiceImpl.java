package pl.patrykdepka.iteventsapp.profileimage.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pl.patrykdepka.iteventsapp.profileimage.exception.DefaultProfileImageNotFoundException;
import pl.patrykdepka.iteventsapp.profileimage.model.ProfileImage;
import pl.patrykdepka.iteventsapp.profileimage.repository.ProfileImageRepository;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ProfileImageServiceImpl implements ProfileImageService {
    private final ProfileImageRepository profileImageRepository;

    public ProfileImageServiceImpl(ProfileImageRepository profileImageRepository) {
        this.profileImageRepository = profileImageRepository;
    }

    public ProfileImage createDefaultProfileImage() {
        ClassPathResource resource = new ClassPathResource("static/images/default_profile_image.png");
        try (InputStream defaultProfileImage = resource.getInputStream()) {
            ProfileImage profileImage = new ProfileImage();
            profileImage.setFileName(resource.getFilename());
            profileImage.setFileType("image/png");
            profileImage.setFileData(defaultProfileImage.readAllBytes());
            return profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new DefaultProfileImageNotFoundException("File " + resource.getPath() + " not found");
        }
    }
}
