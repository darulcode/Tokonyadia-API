package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.UserShippingRequest;
import git.darul.tokonyadia.dto.response.UserShippingResponse;
import git.darul.tokonyadia.service.UserShippingService;
import git.darul.tokonyadia.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constant.USER_SHIPPING_API)
public class UserShippingController {

    private final UserShippingService userShippingService;

    @Operation(summary = "Create User Shipping")
    @PostMapping
    public ResponseEntity<?> createUserShipping(@RequestBody UserShippingRequest request) {
        UserShippingResponse userShippingResponse = userShippingService.create(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Succesfully created user shipping", userShippingResponse);
    }

    @Operation(summary = "Update User Shipping")
    @PutMapping
    public ResponseEntity<?> updateUserShipping(@RequestBody UserShippingRequest request) {
        UserShippingResponse userShippingResponse = userShippingService.update(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully updated user shipping", userShippingResponse);
    }

    @Operation(summary = "Get All User Shipping")
    @GetMapping
    public ResponseEntity<?> getAllUserShipping() {
        Page<UserShippingResponse> allUserShipping = userShippingService.getAllUserShipping();
        return ResponseUtil.buildResponsePage(HttpStatus.OK, "Succesfully retrieved user shipping", allUserShipping);
    }

    @Operation(summary = "Get User Shipping By Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserShippingById(@PathVariable String id) {
        UserShippingResponse userShippingResponse = userShippingService.getById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Succesfully retrieved user shipping", userShippingResponse);
    }

    @Operation(summary = "Delete User Shipping By Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserShipping(@PathVariable String id) {
        userShippingService.delete(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully deleted shipping address", null);
    }
}