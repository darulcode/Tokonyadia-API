package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.response.ProductSizeResponse;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.entity.ProductSize;
import git.darul.tokonyadia.repository.ProductRepository;
import git.darul.tokonyadia.repository.ProductSizeRepository;
import git.darul.tokonyadia.service.ProductSizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSizeServiceImpl implements ProductSizeService {
    private final ProductSizeRepository productSizeRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductSizeResponse> createProductSize(ProductRequest request, Product product) {
        List<ProductSize> productSizeList = new ArrayList<>();
        request.getSize().forEach(size -> {
            ProductSize save = productSizeRepository.save(ProductSize.builder()
                    .product(product)
                    .size(size)
                    .build());
            productSizeList.add(save);
        });
        return productSizeList.stream().map(new Function<ProductSize, ProductSizeResponse>() {
            @Override
            public ProductSizeResponse apply(ProductSize productSize) {
                return ProductSizeResponse.builder()
                        .id(productSize.getId())
                        .size(productSize.getSize())
                        .build();
            }
        }).toList();
    }

    //NOTE:
    @Override
    public List<ProductSizeResponse> updateProductSize(ProductRequest request, Product product) {
        List<ProductSize> productSizeList = new ArrayList<>();
        request.getSize().forEach(size -> {
            ProductSize save = productSizeRepository.save(ProductSize.builder()
                    .product(product)
                    .size(size)
                    .build());
            productSizeList.add(save);
        });
        return productSizeList.stream().map(new Function<ProductSize, ProductSizeResponse>() {
            @Override
            public ProductSizeResponse apply(ProductSize productSize) {
                return ProductSizeResponse.builder()
                        .id(productSize.getId())
                        .size(productSize.getSize())
                        .build();
            }
        }).toList();
    }

    @Override
    public void deleteProductSize(String id) {
        productSizeRepository.findById(id).ifPresentOrElse(
                productSizeRepository::delete,
                () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size ID not found"); }
        );
    }

    @Override
    public List<ProductSizeResponse> getProductSizeByProduct(Product product) {
        List<ProductSize> productList = productSizeRepository.findALLByProduct(product);
        log.info(productList.toString());
        return productList.stream().map(new Function<ProductSize, ProductSizeResponse>() {
            @Override
            public ProductSizeResponse apply(ProductSize productSize) {
                return ProductSizeResponse.builder()
                        .id(productSize.getId())
                        .size(productSize.getSize())
                        .build();
            }
        }).toList();
    }
}
