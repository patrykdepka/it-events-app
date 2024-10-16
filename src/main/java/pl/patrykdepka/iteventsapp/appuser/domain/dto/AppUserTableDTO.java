package pl.patrykdepka.iteventsapp.appuser.domain.dto;

import lombok.Value;

@Value
public class AppUserTableDTO {
    Long id;
    String firstName;
    String lastName;
    String email;

    public static AppUserTableDTOBuilder builder() {
        return new AppUserTableDTOBuilder();
    }

    public static class AppUserTableDTOBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

        public AppUserTableDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AppUserTableDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AppUserTableDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AppUserTableDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AppUserTableDTO build() {
            return new AppUserTableDTO(
                    id,
                    firstName,
                    lastName,
                    email
            );
        }
    }
}
