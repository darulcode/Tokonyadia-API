package git.darul.tokonyadia.entity;

import git.darul.tokonyadia.constant.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = Constant.TABLE_TRANSACTION)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "customer_id",nullable = false)
    private String customerId;

    @Column(name = "store_id", nullable = false)
    private String storeId;


}
