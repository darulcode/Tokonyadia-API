package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.entity.Store;
import git.darul.tokonyadia.repository.StoreRepository;
import git.darul.tokonyadia.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {
    
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store getById(String id) {
        Optional<Store> resultStore = storeRepository.findById(id);
        if (resultStore.isEmpty()) {return null;}
        return resultStore.get();
    }

    @Override
    public Store update(String id, Store store) {
        Optional<Store> storeResult = storeRepository.findById(id);
        if (storeResult.isEmpty()) {return null;}
        return storeRepository.save(store);
    }

    @Override
    public String delete(String id) {
        Optional<Store> storeResult = storeRepository.findById(id);
        if (storeResult.isEmpty()) {return "id not found";}
        storeRepository.deleteById(id);
        return "Store deleted";
    }
}
