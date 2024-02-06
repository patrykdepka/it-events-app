package pl.patrykdepka.iteventsapp.event.domain.dto;

import lombok.Value;

@Value
public class CityDTO {
    String nameWithoutPlCharacters;
    String displayName;
}
