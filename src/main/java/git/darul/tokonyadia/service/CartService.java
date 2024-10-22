package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.CartRequest;
import git.darul.tokonyadia.dto.response.CartResponse;
import git.darul.tokonyadia.entity.Cart;
import org.springframework.data.domain.Page;

public interface CartService {
    CartResponse addCart(CartRequest cartRequest);
    void removeCart(String cartId);
    Page<CartResponse> getAllCart();
    CartResponse updateCart(CartRequest cartRequest);
    Cart getOneCart(String cartId);
}
