package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.repository.ProductRepository;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return null;
    }

    @Override
    public ProductResponse getProductById(String id) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> listProduct = productRepository.findAll();
        List<ProductResponse> productResponseList = new ArrayList<>();
        listProduct.forEach(product -> productResponseList.add(getProductResponse(product)));
        return productResponseList;
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
