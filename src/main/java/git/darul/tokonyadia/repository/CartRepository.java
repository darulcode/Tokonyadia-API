package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.constant.CartStatus;
import git.darul.tokonyadia.entity.Cart;
import git.darul.tokonyadia.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
    Page<Cart> findByUserAccountAndCartStatus(UserAccount userAccount, CartStatus status, Pageable pageable);

}
