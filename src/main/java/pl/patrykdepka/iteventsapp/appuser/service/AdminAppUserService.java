package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserTableDTO;

public interface AdminAppUserService {

    Page<AdminAppUserTableDTO> findAllUsers(Pageable pageable);

    Page<AdminAppUserTableDTO> findUsersBySearch(String searchQuery, Pageable pageable);
}
