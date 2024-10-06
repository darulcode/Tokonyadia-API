package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.StockRequest;
import git.darul.tokonyadia.dto.response.StockResponse;
import git.darul.tokonyadia.entity.Stock;
import git.darul.tokonyadia.entity.Store;
import org.springframework.data.domain.Page;

public interface StockService {
    Page<StockResponse> getAllStock(StockRequest request);
    StockResponse createStock(StockRequest request);
    StockResponse updateStock(String id, Integer quantity);
    StockResponse getById(String id);
}
