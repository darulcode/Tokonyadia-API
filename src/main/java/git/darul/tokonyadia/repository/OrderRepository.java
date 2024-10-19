package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderRepository  extends JpaRepository<Order, String> {
    Page<Order> findAllByUserAccount(UserAccount currentUser, Pageable pageable);
}
