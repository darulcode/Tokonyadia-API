package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.request.ProductSearchRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(ProductRequest productRequest);
    void deleteProduct(String id);
    Page<ProductResponse> getAllProducts(ProductSearchRequest request);
    Product getOne(String id);
}
