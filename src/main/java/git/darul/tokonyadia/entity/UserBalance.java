package git.darul.tokonyadia.entity;

import git.darul.tokonyadia.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Constant.USER_BALANCE_TABLE)
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "balance", columnDefinition = "bigint check (balance > 0)")
    private Long balance;

    @OneToOne
    @JoinColumn(name = "m_user_account")
    private UserAccount userAccount;
}
