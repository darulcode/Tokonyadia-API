package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.CartRequest;
import git.darul.tokonyadia.dto.response.CartResponse;
import git.darul.tokonyadia.service.CartService;
import git.darul.tokonyadia.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Constant.CART_API)
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Get All Cart Item")
    @GetMapping
    public ResponseEntity<?> getAllCart(){
        Page<CartResponse> cart = cartService.getAllCart();
        return ResponseUtil.buildResponsePage(HttpStatus.OK, Constant.SUCCESS_FETCH_ALL_CART_MESSAGE, cart);
    }

    @Operation(summary = "Add Item To Cart")
    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody CartRequest cartRequest){
        CartResponse cartResponse = cartService.addCart(cartRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_ADD_CART_MESSAGE, cartResponse);
    }

    @Operation(summary = "Delete Cart By id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable(name = "id") String id){
        cartService.removeCart(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_DELETE_CART_MESSAGE, null);
    }

    @Operation(summary = "Update Cart")
    @PutMapping
    public ResponseEntity<?> updateCart(@RequestBody CartRequest cartRequest){
        CartResponse cartResponse = cartService.updateCart(cartRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_CART_MESSAGE, cartResponse);
    }
}
