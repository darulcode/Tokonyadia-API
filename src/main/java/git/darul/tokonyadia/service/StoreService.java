package git.darul.tokonyadia.service;

import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.entity.Store;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface StoreService {
    Store create(Store store);
    List<Store> getAll();
    Store getById(String id);
    Store update(String id, Store store);
    String delete(String id);
}
