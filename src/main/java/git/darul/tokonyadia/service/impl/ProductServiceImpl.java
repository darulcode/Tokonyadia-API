package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.request.SearchProductRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.entity.Customer;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.repository.ProductRepository;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.spesification.ProductSpesification;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
        Product productResult = productRepository.saveAndFlush(product);
        return getProductResponse(productResult);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Optional<Product> product= productRepository.findById(id);
        Product productResult = product.isEmpty() ? null : product.get();
        productResult.setName(productRequest.getName());
        productResult.setPrice(productRequest.getPrice());
        productResult.setDescription(productRequest.getDescription());
        productRepository.save(productResult);
        return getProductResponse(productResult);

    }

    @Override
    public Boolean deleteProduct(String id) {
        Optional<Product> productRepositoryById = productRepository.findById(id);
        if (productRepositoryById.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ProductResponse getProductById(String id) {
        Optional<Product> productRepositoryById = productRepository.findById(id);
        if (productRepositoryById.isPresent()) {
            return getProductResponse(productRepositoryById.get());
        }
        return null;
    }

    @Override
    public Page<ProductResponse> getAllProducts(SearchProductRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        Specification<Product> productSpecification = ProductSpesification.menuSpecifiation(request);
        Page<Product> product = productRepository.findAll(productSpecification, pageable);
        return product.map(new Function<Product, ProductResponse>() {
            @Override
            public ProductResponse apply(Product product) {
                return getProductResponse(product);
            }
        });
    }

    @Override
    public Product getOne(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public ProductResponse getProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
