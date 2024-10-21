package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.entity.Image;
import git.darul.tokonyadia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findAllByProduct(Product product);
}
