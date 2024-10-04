package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.SearchStoreRequest;
import git.darul.tokonyadia.dto.request.StoreRequest;
import git.darul.tokonyadia.dto.response.StoreResponse;
import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface StoreService {
    StoreResponse create(StoreRequest request);
    Page<StoreResponse> getAll(SearchStoreRequest request);
    StoreResponse getById(String id);
    StoreResponse update(String id, StoreRequest request);
    Boolean delete(String id);
}
