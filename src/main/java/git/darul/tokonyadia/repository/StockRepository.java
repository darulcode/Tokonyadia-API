package git.darul.tokonyadia.repository;

import git.darul.tokonyadia.dto.response.StockResponse;
import git.darul.tokonyadia.entity.Stock;
import git.darul.tokonyadia.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    Page<Stock> findAllByProductId(String productId, Pageable pageable);
    Page<Stock> findAllByStoreId(String storeId, Pageable pageable);
    Page<Stock> findAllByProductIdAndStoreId(String productId,String storeId, Pageable pageable);
}
