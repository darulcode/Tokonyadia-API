package git.darul.tokonyadia.entity;

import git.darul.tokonyadia.constant.ConditionProduct;
import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Constant.PRODUCT_TABLE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "condition")
    @Enumerated(EnumType.STRING)
    private ConditionProduct condition;

    @Column(nullable = false, name = "price", columnDefinition = "bigint check (price > 0)")
    private Long price;

    @Column(name = "stock", nullable = false, columnDefinition = "int check (stock >= 0)")
    private Integer stock;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "product_status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<ProductSize> productSizes;
}
