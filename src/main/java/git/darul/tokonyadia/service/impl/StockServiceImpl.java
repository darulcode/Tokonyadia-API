package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.StockRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.dto.response.StockResponse;
import git.darul.tokonyadia.dto.response.StoreResponse;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.entity.Stock;
import git.darul.tokonyadia.entity.Store;
import git.darul.tokonyadia.repository.StockRepository;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.service.StockService;
import git.darul.tokonyadia.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class StockServiceImpl implements StockService {

    private final ProductService productService;
    private final StockRepository stockRepository;
    private final StoreService storeService;

    public StockServiceImpl(ProductService productService, StockRepository stockRepository, StoreService storeService) {
        this.productService = productService;
        this.stockRepository = stockRepository;
        this.storeService = storeService;
    }

    @Override
    public Page<StockResponse> getAllStock(StockRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Stock> stock;
        if (request.getProductId() != null && request.getStoreId() != null) {
            stock = stockRepository.findAllByProductIdAndStoreId(request.getProductId(), request.getStoreId(), pageable);
        } else if (request.getStoreId() != null) {
            stock = stockRepository.findAllByStoreId(request.getStoreId(), pageable);
        } else if (request.getProductId() != null) {
            stock = stockRepository.findAllByProductId(request.getProductId(), pageable);
        } else  {
            stock = stockRepository.findAll(pageable);
        }
        return stock.map(new Function<Stock, StockResponse>() {
            @Override
            public StockResponse apply(Stock stock) {
                return getStockResponse(stock);
            }
        });
    }

    @Override
    public StockResponse createStock(StockRequest request) {
        if (request.getProductId() == null || request.getStoreId() == null) {
            return null;
        }
        ProductResponse productResponse = productService.getProductById(request.getProductId());
        StoreResponse storeResponse = storeService.getById(request.getStoreId());
        if (productResponse == null || storeResponse == null) {
            return null;
        }
        Store store = Store.builder().id(storeResponse.getId()).build();
        Product product = Product.builder().id(productResponse.getId()).build();
        Stock stock = Stock.builder()
                .storeId(request.getStoreId())
                .store(store)
                .product(product)
                .productId(product.getId())
                .storeId(store.getId())
                .stock(request.getStock())
                .build();
        Stock stockResult = stockRepository.saveAndFlush(stock);
        return getStockResponse(stockResult);
    }

    @Override
    public StockResponse updateStock(String id, Integer quantity) {
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isEmpty()) {
            return null;
        }
        Stock stockResult = stock.get();
        stockResult.setStock(quantity);
        stockRepository.save(stockResult);
        return getStockResponse(stockResult);
    }

    @Override
    public StockResponse getById(String id) {
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isEmpty()) {
            return null;
        }
        return getStockResponse(stock.get());
    }

    //util stock response
    public StockResponse getStockResponse(Stock stock) {
        ProductResponse product = productService.getProductById(stock.getProductId());
        StoreResponse store = storeService.getById(stock.getStoreId());
        if (product == null || store == null) {
            return null;
        }
        return StockResponse.builder()
                .id(stock.getId())
                .stock(stock.getStock())
                .productName(product.getName())
                .storeName(store.getName())
                .build();
    }

}
