package pl.patrykdepka.iteventsapp.creator;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.iteventsapp.image.domain.Image;

import java.io.IOException;
import java.io.InputStream;

import static pl.patrykdepka.iteventsapp.image.domain.ImageType.PROFILE_IMAGE;

public class ProfileImageCreator {

    public static Image createDefaultProfileImage(Long id) {
        try {
            InputStream imagePath = new ClassPathResource("static/images/default_profile_image.png").getInputStream();
            MockMultipartFile defaultProfileImage = new MockMultipartFile("default_profile_image.png", StreamUtils.copyToByteArray(imagePath));
            Image image = new Image();
            image.setId(id);
            image.setType(PROFILE_IMAGE);
            image.setFileName(defaultProfileImage.getOriginalFilename());
            image.setContentType(defaultProfileImage.getContentType());
            image.setFileData(defaultProfileImage.getBytes());
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MultipartFile createNewProfileImageFile() {
        try {
            InputStream imagePath = new ClassPathResource("static/images/custom_profile_image.png").getInputStream();
            return new MockMultipartFile("custom_profile_image.png", StreamUtils.copyToByteArray(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image createNewProfileImage(Long id) {
        try {
            InputStream imagePath = new ClassPathResource("static/images/custom_profile_image.png").getInputStream();
            MockMultipartFile defaultProfileImage = new MockMultipartFile("custom_profile_image.png", StreamUtils.copyToByteArray(imagePath));
            Image image = new Image();
            image.setId(id);
            image.setType(PROFILE_IMAGE);
            image.setFileName(defaultProfileImage.getOriginalFilename());
            image.setContentType(defaultProfileImage.getContentType());
            image.setFileData(defaultProfileImage.getBytes());
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
