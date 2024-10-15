package git.darul.tokonyadia.entity;


import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.constant.UserType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Constant.USER_TABLE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "m_user_account")
    private UserAccount userAccount;
}
