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
@Table(name = Constant.TABLE_STOCK)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "stock")
    private Integer stock = 0;
}
