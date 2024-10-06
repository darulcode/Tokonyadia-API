package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    Page<Store> findAll(Specification<Store> storeSpecification, Pageable pageable);
}
