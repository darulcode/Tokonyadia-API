package git.darul.tokonyadia.entity;


import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.constant.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Constant.USER_ACCOUNT_TABLE)
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false, name = "username")
    private String username;

    @Column( nullable = false, name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    // Relasi One-to-Many dengan UserShipping
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<UserShipping> userShipping;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userType.name())); // Menambahkan role berdasarkan userType
        return authorities;
    }
}
