package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.CartStatus;
import git.darul.tokonyadia.dto.request.CartRequest;
import git.darul.tokonyadia.dto.response.CartResponse;
import git.darul.tokonyadia.dto.response.ProductSizeResponse;
import git.darul.tokonyadia.entity.Cart;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.repository.CartRepository;
import git.darul.tokonyadia.service.CartService;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.service.ProductSizeService;
import git.darul.tokonyadia.util.AuthenticationContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ProductSizeService productSizeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartResponse addCart(CartRequest cartRequest) {
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Product product = productService.getOne(cartRequest.getProductId());
        if (product.getStock() < cartRequest.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock");
        }
        List<ProductSizeResponse> productSizes = productSizeService.getProductSizeByProduct(product);
        Boolean sizeExists = productSizes.stream()
                .anyMatch(ps -> ps.getSize().equals(cartRequest.getSize()));
        if (!sizeExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size not found");
        }
        Cart cart = Cart.builder()
                .cartStatus(CartStatus.ACTIVE)
                .quantity(cartRequest.getQuantity())
                .size(cartRequest.getSize())
                .userAccount(currentUser)
                .product(product)
                .build();

        cartRepository.saveAndFlush(cart);
        productService.addStock(product.getId(), cartRequest.getQuantity());
        return getCartResponse(cart);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeCart(String id) {
        cartRepository.findById(id).ifPresentOrElse(
                cart -> {
                    UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
                    if (!currentUser.getId().equals(cart.getUserAccount().getId()) || currentUser == null) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                    }
                    Product product = productService.getOne(cart.getProduct().getId());
                    productService.addStock(product.getId(), product.getStock() + cart.getQuantity());
                    cart.setCartStatus(CartStatus.INACTIVE);
                    cart.setQuantity(0);
                    cartRepository.save(cart);
                },
                () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"); }
        );

    }

    @Transactional(readOnly = true)
    @Override
    public Page<CartResponse> getAllCart() {
        Pageable pageable = PageRequest.of(0, 10);
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Page<Cart> cartList = cartRepository.findByUserAccount(currentUser, pageable);
        return cartList.map(cart -> getCartResponse(cart));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartResponse updateCart(CartRequest cartRequest) {
        Cart cart = cartRepository.findById(cartRequest.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        Product product = productService.getOne(cart.getProduct().getId());
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (!currentUser.getId().equals(cart.getUserAccount().getId()) || currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        log.info("stok + quantity : {}", product.getStock() + cart.getQuantity());
        log.info("stok  : {}", product.getStock() );
        if (product.getStock() + cart.getQuantity() < cartRequest.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock");
        }
        log.info("update stok :{}", (cart.getQuantity() + product.getStock()) - cartRequest.getQuantity());
        productService.addStock(product.getId(), (cart.getQuantity() + product.getStock()) - cartRequest.getQuantity());
        cart.setQuantity(cartRequest.getQuantity());
        cartRepository.saveAndFlush(cart);
        return getCartResponse(cart);
    }

    private CartResponse getCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .size(cart.getSize())
                .productId(cart.getProduct().getId())
                .userAccountId(cart.getUserAccount().getId())
                .statusCart(cart.getCartStatus().getDescription())
                .price(cart.getProduct().getPrice())
                .totalPrice(cart.getProduct().getPrice() * cart.getQuantity())
                .build();
    }
}
