package pl.patrykdepka.iteventsapp.appuser.model;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.iteventsapp.core.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class AppUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
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
