package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.repository.ProductRepository;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.PRODUCT_API)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        return ResponseUtil.buildResponseEntity(HttpStatus.CREATED, "Succesfully created product", product);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductResponse> allProducts = productService.getAllProducts();
        return ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully get all data product", allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getProductById(id);
        if (product == null) {
            return ResponseUtil.buildResponseEntity(HttpStatus.NOT_FOUND, "Product not found", null);
        }
        return ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully get data product", product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id ,@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.updateProduct(id, request);
        if (productResponse == null) {
            return ResponseUtil.buildResponseEntity(HttpStatus.NOT_FOUND, "Product not found", null);
        }
        return ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully updated product", productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        Boolean productResponse = productService.deleteProduct(id);
        if (!productResponse) {
            return ResponseUtil.buildResponseEntity(HttpStatus.NOT_FOUND, "Product not found", null);
        }
        return ResponseUtil.buildResponseEntity(HttpStatus.OK, "Succesfully deleted product", null);
    }
}
