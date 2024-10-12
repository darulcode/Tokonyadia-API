package git.darul.tokonyadia.entity;


import git.darul.tokonyadia.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = Constant.TABLE_CUSTOMER)
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name",nullable = false, length = 50)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_account")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

}
