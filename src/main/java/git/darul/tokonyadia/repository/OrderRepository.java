package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderRepository  extends JpaRepository<Order, String> {
}
