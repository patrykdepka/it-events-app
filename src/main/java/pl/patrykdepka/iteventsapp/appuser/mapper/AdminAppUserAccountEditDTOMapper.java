package pl.patrykdepka.iteventsapp.appuser.mapper;

import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserAccountEditDTO;
import pl.patrykdepka.iteventsapp.appuser.model.AppUser;

public class AdminAppUserAccountEditDTOMapper {

    private AdminAppUserAccountEditDTOMapper() {
    }

    public static AdminAppUserAccountEditDTO mapToAdminAppUserAccountEditDTO(AppUser user) {
        return AdminAppUserAccountEditDTO.builder()
                .id(user.getId())
                .enabled(user.isEnabled())
                .accountNonLocked(user.isAccountNonLocked())
                .roles(user.getRoles())
                .build();
    }
}
