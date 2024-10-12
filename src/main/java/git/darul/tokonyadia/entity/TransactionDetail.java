package git.darul.tokonyadia.entity;

import git.darul.tokonyadia.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = Constant.TABLE_TRANSACTION_DETAIL)
@Builder
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_price", nullable = false)
    private Long productPrice;

    @Column(name = "quantity", nullable = false, columnDefinition = "int check (quantity > 0)")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false, referencedColumnName = "id")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodcut_id", nullable = false, referencedColumnName = "id")
    private Product product;
}
