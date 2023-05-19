package pl.patrykdepka.iteventsapp.appuser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.mapper.AdminAppUserTableDTOMapper;
import pl.patrykdepka.iteventsapp.appuser.repository.AppUserRepository;
import pl.patrykdepka.iteventsapp.appuser.specification.AppUserSpecification;

@Service
public class AdminAppUserServiceImpl implements AdminAppUserService {
    private final AppUserRepository appUserRepository;

    public AdminAppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Page<AdminAppUserTableDTO> findAllUsers(Pageable pageable) {
        return AdminAppUserTableDTOMapper.mapToAdminAppUserTableDTOs(appUserRepository.findAll(pageable));
    }

    public Page<AdminAppUserTableDTO> findUsersBySearch(String searchQuery, Pageable pageable) {
        searchQuery = searchQuery.toLowerCase();
        String[] searchWords = searchQuery.split(" ");

        if (searchWords.length == 1 && !"".equals(searchWords[0])) {
            return AdminAppUserTableDTOMapper
                    .mapToAdminAppUserTableDTOs(appUserRepository.findAll(AppUserSpecification.bySearch(searchWords[0]), pageable));
        }
        if (searchWords.length == 2) {
            return AdminAppUserTableDTOMapper
                    .mapToAdminAppUserTableDTOs(appUserRepository.findAll(AppUserSpecification.bySearch(searchWords[0], searchWords[1]), pageable));
        }

        return Page.empty();
    }
}
