package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.entity.UserShipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserShippingRepository extends JpaRepository<UserShipping, String> {
    Page<UserShipping> findAllByUserAccount(UserAccount userAccount, Pageable pageable);
}
