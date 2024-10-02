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
@Table(name = Constant.TABLE_TRANSACTION_DETAIL)
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "transaction_id",nullable = false)
    private String transactionId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_price", nullable = false)
    private Long productPrice;
}
