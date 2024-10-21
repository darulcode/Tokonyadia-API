package git.darul.tokonyadia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.request.ProductSearchRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.PRODUCT_API)
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "Create Product")
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestParam(name = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(name = "product") String product) {
        try {
            ProductRequest request = objectMapper.readValue(product, ProductRequest.class);
            ProductResponse productResult = productService.createProduct(request, multipartFiles);
            return ResponseUtil.buildResponse(HttpStatus.CREATED, "Successfully created product", productResult);
        } catch (Exception e) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @Operation(summary = "Update Product")
    @PreAuthorize("hasRole('SELLER')")
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(productRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated product", productResponse);
    }

    @Operation(summary = "Delete Product By Id")
    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully deleted product", id);
    }


    @Operation(summary = "Get All product ")
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "name", required = false) String query,
            @RequestParam(name = "minPrice", required = false) Long minPrice,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice
    ) {
        log.info("Get all products");
        ProductSearchRequest productSearchRequest = ProductSearchRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .query(query)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        Page<ProductResponse> allProducts = productService.getAllProducts(productSearchRequest);
        return ResponseUtil.buildResponsePage(HttpStatus.OK, "Succesfully fetch all product", allProducts);
    }

    @Operation(summary = "Get Product By Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        log.info("Get product");
        ProductResponse product = productService.getProductById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully fetched product", product);
    }
}
