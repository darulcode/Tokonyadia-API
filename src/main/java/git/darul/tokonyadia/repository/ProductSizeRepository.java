package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ProductSizeRepository extends JpaRepository<ProductSize, String> {
    List<ProductSize> findALLByProduct(Product product);
}
