package pl.patrykdepka.iteventsapp.appuser.model;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.iteventsapp.core.BaseEntity;
import pl.patrykdepka.iteventsapp.profileimage.model.ProfileImage;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class AppUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "app_user_profile_image",
            joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
    )
    private ProfileImage profileImage;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String city;
    private String bio;
    private String email;
    private String password;
    private boolean enabled;
    private boolean accountNonLocked;
    @ElementCollection
    @CollectionTable(name = "app_user_roles", joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
}
