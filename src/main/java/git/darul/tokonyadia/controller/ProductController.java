package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.request.SearchProductRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Succesfully created product", product);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "page",required = false, defaultValue = "1") Integer page,
                                            @RequestParam(name = "size",required = false, defaultValue = "10") Integer size,
                                            @RequestParam(name = "name", required = false) String name,
                                            @RequestParam(name = "minPrice", required = false) Long minPrice,
                                            @RequestParam(name = "maxPrice", required = false) Long maxPrice) {

        SearchProductRequest productRequest = SearchProductRequest.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .page(page)
                .size(size)
                .build();
        Page<ProductResponse> allProducts = productService.getAllProducts(productRequest);
        return ResponseUtil.buildResponsePaging(HttpStatus.OK, "Succesfully get all data product", allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getProductById(id);
        if (product == null) {
            return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Product not found", null);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully get data product", product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id ,@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.updateProduct(id, request);
        if (productResponse == null) {
            return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Product not found", null);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully updated product", productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        Boolean productResponse = productService.deleteProduct(id);
        if (!productResponse) {
            return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Product not found", null);
        }
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully deleted product", null);
    }
}
