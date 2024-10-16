package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.request.ProductSizeRequest;
import git.darul.tokonyadia.dto.response.ProductSizeResponse;
import git.darul.tokonyadia.entity.Product;

import java.util.List;

public interface ProductSizeService {
    List<ProductSizeResponse> createProductSize(ProductRequest request, Product product);
    List<ProductSizeResponse> updateProductSize(ProductRequest request, Product product);
    void deleteProductSize(String id);
    void updateByid(ProductSizeRequest request);
    List<ProductSizeResponse> getProductSizeByProduct(Product product);

}
