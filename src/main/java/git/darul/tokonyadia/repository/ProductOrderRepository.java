package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ProductOrderRepository extends JpaRepository<ProductOrder, String> {

    List<ProductOrder> findAllByOrder(Order order);
}
