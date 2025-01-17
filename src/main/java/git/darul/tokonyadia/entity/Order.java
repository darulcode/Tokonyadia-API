package git.darul.tokonyadia.entity;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.constant.PaymentMethod;
import git.darul.tokonyadia.constant.ShippingMethod;
import git.darul.tokonyadia.constant.StatusOrder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Constant.ORDER_TABLE)
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreatedDate
    @Column(nullable = false, name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_account_id")
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "shipping_method")
    private ShippingMethod shippingMethod;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private StatusOrder status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<ProductOrder> productOrders;
}
