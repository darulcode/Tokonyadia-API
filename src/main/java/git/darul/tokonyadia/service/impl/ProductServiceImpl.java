package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.ConditionProduct;
import git.darul.tokonyadia.constant.ProductStatus;
import git.darul.tokonyadia.constant.UserType;
import git.darul.tokonyadia.dto.request.ProductRequest;
import git.darul.tokonyadia.dto.request.ProductSearchRequest;
import git.darul.tokonyadia.dto.response.ProductResponse;
import git.darul.tokonyadia.dto.response.ProductSizeResponse;
import git.darul.tokonyadia.entity.Category;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.repository.ProductRepository;
import git.darul.tokonyadia.service.CategoryService;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.service.ProductSizeService;
import git.darul.tokonyadia.spesification.ProductSpecification;
import git.darul.tokonyadia.util.AuthenticationContextUtil;
import git.darul.tokonyadia.util.ShortUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductSizeService productSizeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (currentUser == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        if (currentUser.getUserType().equals(UserType.ROLE_BUYER) ) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        Category category = categoryService.getOne(request.getCategoryId());
        log.info("product condition :{}", request.getCondition());
        log.info("product condition : {}", ConditionProduct.fromDescription(request.getCondition()));
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .productStatus(ProductStatus.AVAILABLE)
                .category(category)
                .stock(request.getStock())
                .condition(ConditionProduct.fromDescription(request.getCondition()))
                .build();
        productRepository.saveAndFlush(product);
        List<ProductSizeResponse> productSize = productSizeService.createProductSize(request, product);
        return getProductResponse(product, productSize);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse updateProduct(ProductRequest request) {
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (currentUser.getUserType().equals(UserType.ROLE_BUYER)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        Category category = categoryService.getOne(request.getCategoryId());
        Product product = getOne(request.getId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        product.setStock(request.getStock());
        product.setCondition(ConditionProduct.fromDescription(request.getCondition()));
        Product productResult = productRepository.save(product);
        List<ProductSizeResponse> productSizeResponses = productSizeService.updateProductSize(request, productResult);
        return getProductResponse(productResult, productSizeResponses);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProduct(String id) {
        Product product = getOne(id);
        product.setProductStatus(ProductStatus.DELETE);
        productRepository.save(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(ProductSearchRequest request) {
        Sort sortBy = ShortUtil.parseSort(request.getSortBy());
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sortBy);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = authentication.getPrincipal().toString();
        Specification<Product> specification;
        log.info("principal :{}", principal);
        if ("anonymousUser".equals(principal)) {
            specification = ProductSpecification.getSpecification(request, true);
        } else {
            UserAccount userAccount = (UserAccount) authentication.getPrincipal();
            if (UserType.ROLE_BUYER.equals(userAccount.getUserType())) {
                specification = ProductSpecification.getSpecification(request, true);
            } else {
                log.info("principal : {}", principal);
                specification = ProductSpecification.getSpecification(request, false);
            }
        }
        Page<Product> productAll = productRepository.findAll(specification, pageable);
        return productAll.map(product -> {
            List<ProductSizeResponse> productSizeByProductId = productSizeService.getProductSizeByProduct(product);
            return getProductResponse(product, productSizeByProductId);
        });
//        return productAll.map(new Function<Product, ProductResponse>() {
//            @Override
//            public ProductResponse apply(Product product) {
//                List<ProductSizeResponse> productSizeByProductId = productSizeService.getProductSizeByProduct(product);
//                return getProductResponse(product, productSizeByProductId);
//            }
//        });
    }

    @Override
    public Product getOne(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Override
    public void addStock(String id, Integer quantity) {
        Product product = getOne(id);
        product.setStock(quantity);
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = getOne(id);
        List<ProductSizeResponse> productSizeByProduct = productSizeService.getProductSizeByProduct(product);
        return getProductResponse(product, productSizeByProduct);
    }

    private ProductResponse getProductResponse(Product product, List<ProductSizeResponse> productSize) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .condition(product.getCondition().getDescription())
                .categoryId(product.getCategory().getId())
                .statusProduct(product.getProductStatus().getDescription())
                .stock(product.getStock())
                .productSize(productSize)
                .build();
    }
}
