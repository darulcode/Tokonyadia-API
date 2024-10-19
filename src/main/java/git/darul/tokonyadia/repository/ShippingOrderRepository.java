package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, String> {
    Optional<ShippingOrder> findByOrder(Order order);
}
