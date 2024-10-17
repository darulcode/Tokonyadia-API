package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, String> {

    List<ProductOrder> findAllByOrder(Order order);
}
