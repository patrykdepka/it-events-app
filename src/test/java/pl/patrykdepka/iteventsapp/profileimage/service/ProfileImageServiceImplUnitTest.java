package pl.patrykdepka.iteventsapp.profileimage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUser;
import pl.patrykdepka.iteventsapp.creator.AppUserCreator;
import pl.patrykdepka.iteventsapp.creator.ProfileImageCreator;
import pl.patrykdepka.iteventsapp.profileimage.model.ProfileImage;
import pl.patrykdepka.iteventsapp.profileimage.repository.ProfileImageRepository;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class ProfileImageServiceImplUnitTest {
    private ProfileImageRepository profileImageRepository;
    private ProfileImageServiceImpl profileImageServiceImpl;

    @BeforeEach
    void setUp() {
        profileImageRepository = Mockito.mock(ProfileImageRepository.class);
        profileImageServiceImpl = new ProfileImageServiceImpl(profileImageRepository);
    }

    @Test
    void shouldReturnSavedDefaultProfileImage() {
        // given
        when(profileImageRepository.save(any(ProfileImage.class))).thenAnswer(i -> i.getArguments()[0]);
        // when
        ProfileImage createdDefaultProfileImage = profileImageServiceImpl.createDefaultProfileImage();
        // then
        verify(profileImageRepository, times(1)).save(argThat((ProfileImage savedDefaultProfileImage) -> {
            Assertions.assertAll("Testing saved default profile image",
                    () -> assertNull(savedDefaultProfileImage.getId()),
                    () -> assertEquals(createdDefaultProfileImage.getFileName(), savedDefaultProfileImage.getFileName()),
                    () -> assertEquals(createdDefaultProfileImage.getFileType(), savedDefaultProfileImage.getFileType()),
                    () -> assertEquals(createdDefaultProfileImage.getFileData(), savedDefaultProfileImage.getFileData())
            );
            return true;
        }));
    }

    @Test
    void shouldReturnUpdatedProfileImage() throws IOException {
        // given
        AppUser user = AppUserCreator.create(2L, "Jan", "Kowalski");
        MultipartFile newProfileImageFile = ProfileImageCreator.createNewProfileImageFile();
        // when
        Optional<ProfileImage> updatedProfileImage = profileImageServiceImpl.updateProfileImage(user, newProfileImageFile);
        // then
        assertThat(updatedProfileImage.isPresent()).isTrue();
        assertThat(updatedProfileImage.get().getId()).isNotNull();
        assertThat(updatedProfileImage.get().getFileName()).isEqualTo(newProfileImageFile.getOriginalFilename());
        assertThat(updatedProfileImage.get().getFileType()).isEqualTo(newProfileImageFile.getContentType());
        assertThat(updatedProfileImage.get().getFileData()).isEqualTo(newProfileImageFile.getBytes());
    }
}
