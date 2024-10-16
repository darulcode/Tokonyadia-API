package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSizeRepository extends JpaRepository<ProductSize, String> {
    List<ProductSize> findALLByProduct(Product product);
}
