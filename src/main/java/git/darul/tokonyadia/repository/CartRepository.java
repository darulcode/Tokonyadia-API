package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Cart;
import git.darul.tokonyadia.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
    Page<Cart> findByUserAccount(UserAccount userAccount, Pageable pageable);
}
