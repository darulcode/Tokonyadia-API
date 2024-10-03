package git.darul.tokonyadia.service;


import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(String id,ProductRequest productRequest);
    Boolean deleteProduct(String id);
    ProductResponse getProductById(String id);
    List<ProductResponse> getAllProducts();
}
