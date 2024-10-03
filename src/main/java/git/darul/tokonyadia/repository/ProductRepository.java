package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
