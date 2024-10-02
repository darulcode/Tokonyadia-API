package git.darul.tokonyadia.entity;


import git.darul.tokonyadia.constant.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = Constant.TABLE_STORE)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "no_siup",unique = true, nullable = false)
    private String noSiup;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;
}
