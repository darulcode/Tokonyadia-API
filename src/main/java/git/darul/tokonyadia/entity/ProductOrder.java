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
@Table(name = Constant.PRODUCT_ORDER_DETAIL_TABLE)
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false, columnDefinition = "int check (quantity > 0)")
    private Integer quantity;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "price", nullable = false, columnDefinition = "bigint check (price > 0)")
    private Long price;
}
