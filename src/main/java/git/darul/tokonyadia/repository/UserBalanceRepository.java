package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, String> {
    Optional<UserBalance> findByUserAccount(UserAccount userAccount);
}
