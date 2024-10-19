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
@Table(name = Constant.USER_SHIPPING_TABLE)
public class UserShipping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;
    @Column(nullable = false, name = "city")
    private String city;

    // Relasi Many-to-One dengan UserAccount
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;
}
