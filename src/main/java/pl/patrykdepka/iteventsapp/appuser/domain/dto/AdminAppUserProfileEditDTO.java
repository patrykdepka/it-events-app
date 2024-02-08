package pl.patrykdepka.iteventsapp.appuser.domain.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.iteventsapp.core.DateTime;
import pl.patrykdepka.iteventsapp.core.Image;
import pl.patrykdepka.iteventsapp.image.domain.ImageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class AdminAppUserProfileEditDTO {
    ImageType profileImageType;
    String profileImageData;
    @Image(width = 250, height = 250)
    MultipartFile profileImage;
    @NotBlank(message = "{form.field.firstName.error.notBlank.message}")
    @Size(min = 2, max = 50, message = "{form.field.firstName.error.size.message}")
    String firstName;
    @NotBlank(message = "{form.field.lastName.error.notBlank.message}")
    @Size(min = 2, max = 50, message = "{form.field.lastName.error.size.message}")
    String lastName;
    @NotNull(message = "{form.field.dateOfBirth.error.notNull.message}")
    @DateTime(iso = DateTime.ISO.DATE)
    String dateOfBirth;
    @Size(max = 50, message = "{form.field.city.error.size.message}")
    String city;
    @Size(max = 1000, message = "{form.field.bio.error.size.message}")
    String bio;

    public static AdminAppUserProfileEditDTOBuilder builder() {
        return new AdminAppUserProfileEditDTOBuilder();
    }

    public static class AdminAppUserProfileEditDTOBuilder {
        private ImageType profileImageType;
        private String profileImageData;
        private MultipartFile profileImage;
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String city;
        private String bio;

        public AdminAppUserProfileEditDTOBuilder profileImageType(ImageType profileImageType) {
            this.profileImageType = profileImageType;
            return this;
        }

        public AdminAppUserProfileEditDTOBuilder profileImageData(String profileImageData) {
            this.profileImageData = profileImageData;
            return this;
        }

        public AdminAppUserProfileEditDTOBuilder profileImage(MultipartFile profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public AdminAppUserProfileEditDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AdminAppUserProfileEditDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AdminAppUserProfileEditDTOBuilder dateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AdminAppUserProfileEditDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AdminAppUserProfileEditDTOBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public AdminAppUserProfileEditDTO build() {
            return new AdminAppUserProfileEditDTO(
                    profileImageType,
                    profileImageData,
                    profileImage,
                    firstName,
                    lastName,
                    dateOfBirth,
                    city,
                    bio
            );
        }
    }
}
