package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.StoreRequest;
import git.darul.tokonyadia.dto.response.CustomerResponse;
import git.darul.tokonyadia.dto.response.StoreResponse;
import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.entity.Store;
import git.darul.tokonyadia.repository.StoreRepository;
import git.darul.tokonyadia.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {
    
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public StoreResponse create(StoreRequest request) {
        Store store = Store.builder()
                .name(request.getName())
                .noSiup(request.getNoSiup())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
        Store storeResult = storeRepository.save(store);
        return getStoreResponse(storeResult);
    }

    @Override
    public List<StoreResponse> getAll() {
        List<Store> storesResult = storeRepository.findAll();
        List<StoreResponse> storeResponses = new ArrayList<>();
        storesResult.forEach(store -> storeResponses.add(getStoreResponse(store)));
        return storeResponses;
    }

    @Override
    public StoreResponse getById(String id) {
        Optional<Store> resultStore = storeRepository.findById(id);
        if (resultStore.isEmpty()) {return null;}
        return getStoreResponse(resultStore.get());
    }

    @Override
    public StoreResponse update(String id, StoreRequest request) {
        Optional<Store> storeResult = storeRepository.findById(id);
        if (storeResult.isEmpty()) {return null;}
        Store store = Store.builder()
                .id(id)
                .name(request.getName())
                .noSiup(request.getNoSiup())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
        storeRepository.save(store);
        return getStoreResponse(store);

    }

    @Override
    public Boolean delete(String id) {
        Optional<Store> storeResult = storeRepository.findById(id);
        if (storeResult.isEmpty()) {return false;}
        storeRepository.deleteById(id);
        return true;
    }

    private StoreResponse getStoreResponse(Store store) {
        if (store == null) {return null;}
        return  StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .noSiup(store.getNoSiup())
                .build();

    }
}
