package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.User;
import git.darul.tokonyadia.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAll(Specification<User> userSpecification, Pageable pageable);
    User findByUserAccount(UserAccount userAccount);

    User findByUserAccountId(String id );
}
