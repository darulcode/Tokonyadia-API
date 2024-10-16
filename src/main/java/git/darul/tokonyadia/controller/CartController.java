package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.CartRequest;
import git.darul.tokonyadia.dto.response.CartResponse;
import git.darul.tokonyadia.service.CartService;
import git.darul.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Constant.CART_API)
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getAllCart(){
        Page<CartResponse> cart = cartService.getAllCart();
        return ResponseUtil.buildResponsePage(HttpStatus.OK, "Successfully fetch all cart.", cart);
    }

    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody CartRequest cartRequest){
        CartResponse cartResponse = cartService.addCart(cartRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully added cart.", cartResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable(name = "id") String id){
        cartService.removeCart(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully deleted cart.", null);
    }

    @PutMapping
    public ResponseEntity<?> updateCart(@RequestBody CartRequest cartRequest){
        CartResponse cartResponse = cartService.updateCart(cartRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated cart.", cartResponse);
    }
}
