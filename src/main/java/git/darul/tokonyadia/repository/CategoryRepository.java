package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.constant.CategoryStatus;
import git.darul.tokonyadia.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(String name);
    Optional<Category> findByIdAndStatus(String id, CategoryStatus status);
    Page<Category> findAllByStatus( Pageable pageable,CategoryStatus status);
    boolean existsByName(String name);
}
