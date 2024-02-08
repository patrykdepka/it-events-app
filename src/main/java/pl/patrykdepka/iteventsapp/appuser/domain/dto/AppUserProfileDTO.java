package pl.patrykdepka.iteventsapp.appuser.domain.dto;

import lombok.Value;
import pl.patrykdepka.iteventsapp.image.domain.ImageType;

@Value
public class AppUserProfileDTO {
    ImageType profileImageType;
    String profileImageData;
    String firstName;
    String lastName;
    String dateOfBirth;
    String city;
    String bio;

    public static AppUserProfileDTOBuilder builder() {
        return new AppUserProfileDTOBuilder();
    }

    public static class AppUserProfileDTOBuilder {
        private ImageType profileImageType;
        private String profileImageData;
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String city;
        private String bio;

        public AppUserProfileDTOBuilder profileImageType(ImageType profileImageType) {
            this.profileImageType = profileImageType;
            return this;
        }

        public AppUserProfileDTOBuilder profileImageData(String profileImageData) {
            this.profileImageData = profileImageData;
            return this;
        }

        public AppUserProfileDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AppUserProfileDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AppUserProfileDTOBuilder dateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AppUserProfileDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AppUserProfileDTOBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public AppUserProfileDTO build() {
            return new AppUserProfileDTO(
                    profileImageType,
                    profileImageData,
                    firstName,
                    lastName,
                    dateOfBirth,
                    city,
                    bio
            );
        }
    }
}
