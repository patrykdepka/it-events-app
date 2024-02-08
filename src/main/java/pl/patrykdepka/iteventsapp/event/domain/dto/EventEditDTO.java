package pl.patrykdepka.iteventsapp.event.domain.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.iteventsapp.core.DateTime;
import pl.patrykdepka.iteventsapp.core.Image;
import pl.patrykdepka.iteventsapp.event.domain.AdmissionType;
import pl.patrykdepka.iteventsapp.event.domain.EventType;
import pl.patrykdepka.iteventsapp.image.domain.ImageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class EventEditDTO {
    @NotBlank(message = "{form.field.name.error.notBlank.message}")
    String name;
    ImageType eventImageType;
    String eventImageData;
    @Image(width = 480, height = 270)
    MultipartFile eventImage;
    @NotNull(message = "{form.field.eventType.error.notNull.message}")
    EventType eventType;
    @NotNull(message = "{form.field.dateOfBirth.error.notNull.message}")
    @DateTime(message = "{validation.annotation.DateTime.dateTime.invalidFormat.message}", iso = DateTime.ISO.DATE_TIME)
    String dateTime;
    @NotBlank(message = "{form.field.language.error.notBlank.message}")
    String language;
    @NotNull(message = "{form.field.admission.error.notNull.message}")
    AdmissionType admission;
    @NotBlank(message = "{form.field.city.error.notBlank.message}")
    String city;
    @NotBlank(message = "{form.field.location.error.notBlank.message}")
    String location;
    @NotBlank(message = "{form.field.address.error.notBlank.message}")
    String address;
    @NotBlank(message = "{form.field.description.error.notBlank.message}")
    String description;

    public static EventEditDTOBuilder builder() {
        return new EventEditDTOBuilder();
    }

    public static class EventEditDTOBuilder {
        private String name;
        private ImageType eventImageType;
        private String eventImageData;
        private MultipartFile eventImage;
        private EventType eventType;
        private String dateTime;
        private String language;
        private AdmissionType admission;
        private String city;
        private String location;
        private String address;
        private String description;

        public EventEditDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EventEditDTOBuilder eventImageType(ImageType eventImageType) {
            this.eventImageType = eventImageType;
            return this;
        }

        public EventEditDTOBuilder eventImageData(String eventImageData) {
            this.eventImageData = eventImageData;
            return this;
        }

        public EventEditDTOBuilder eventImage(MultipartFile eventImage) {
            this.eventImage = eventImage;
            return this;
        }

        public EventEditDTOBuilder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public EventEditDTOBuilder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public EventEditDTOBuilder language(String language) {
            this.language = language;
            return this;
        }

        public EventEditDTOBuilder admission(AdmissionType admission) {
            this.admission = admission;
            return this;
        }

        public EventEditDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public EventEditDTOBuilder location(String location) {
            this.location = location;
            return this;
        }

        public EventEditDTOBuilder address(String address) {
            this.address = address;
            return this;
        }

        public EventEditDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EventEditDTO build() {
            return new EventEditDTO(
                    name,
                    eventImageType,
                    eventImageData,
                    eventImage,
                    eventType,
                    dateTime,
                    language,
                    admission,
                    city,
                    location,
                    address,
                    description
            );
        }
    }
}
