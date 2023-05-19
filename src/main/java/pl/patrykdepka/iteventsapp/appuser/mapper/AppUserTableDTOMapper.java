package pl.patrykdepka.iteventsapp.appuser.mapper;

import pl.patrykdepka.iteventsapp.appuser.dto.AppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserTableDTOMapper {

    private AppUserTableDTOMapper() {
    }

    public static List<AppUserTableDTO> mapToAppUserTableDTOs(List<AppUser> users) {
        return users
                .stream()
                .map(AppUserTableDTOMapper::mapToAppUserTableDTO)
                .collect(Collectors.toList());
    }

    private static AppUserTableDTO mapToAppUserTableDTO(AppUser user) {
        return AppUserTableDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
