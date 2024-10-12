package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.SearchStoreRequest;
import git.darul.tokonyadia.dto.request.StoreRequest;
import git.darul.tokonyadia.dto.response.StoreResponse;
import git.darul.tokonyadia.entity.Store;
import git.darul.tokonyadia.repository.StoreRepository;
import git.darul.tokonyadia.service.StoreService;
import git.darul.tokonyadia.spesification.StoreSpesification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class StoreServiceImpl implements StoreService {
    
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public StoreResponse create(StoreRequest request) {
        System.out.println(request.getNoSiup());
        System.out.println(request.getName());
        System.out.println(request.getAddress());
        System.out.println(request.getPhoneNumber());
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
    public Page<StoreResponse> getAll(SearchStoreRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Specification<Store> storeSpecification = StoreSpesification.spesificationStore(request);
        Page<Store> storeResult = storeRepository.findAll(storeSpecification, pageable);
        return storeResult.map(new Function<Store, StoreResponse>() {
            @Override
            public StoreResponse apply(Store store) {
                return getStoreResponse(store);
            }
        });
    }

    @Override
    public StoreResponse getById(String id) {
        Optional<Store> resultStore = storeRepository.findById(id);
        if (resultStore.isEmpty()) {return null;}
        return getStoreResponse(resultStore.get());
    }

    @Override
    public Store getOne(String id) {
        Optional<Store> resultStore = storeRepository.findById(id);
        return resultStore.orElse(null);
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
