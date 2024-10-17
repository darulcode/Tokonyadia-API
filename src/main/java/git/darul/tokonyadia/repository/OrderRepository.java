package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, String> {
}
