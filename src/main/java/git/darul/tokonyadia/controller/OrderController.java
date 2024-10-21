package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.OrderRequest;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.request.UpdateOrderRequest;
import git.darul.tokonyadia.dto.response.OrderResponse;
import git.darul.tokonyadia.service.OrderService;
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

@RestController
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@RequestMapping(Constant.ORDER_API)
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create Order")
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Succesfully created Order", orderResponse);
    }

    @Operation(summary = "Get All Orders")
    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy
    ) {
        PagingAndShortingRequest request = PagingAndShortingRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
        Page<OrderResponse> ordersResult = orderService.getAllOrders(request);
        return ResponseUtil.buildResponsePage(HttpStatus.OK, "Successfully fetch all orders", ordersResult);
    }

    @Operation(summary = "Get Order By Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        OrderResponse orderResponse = orderService.getById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully fetched Order", orderResponse);
    }

    @Operation(summary = "Update Status Order")
    @PreAuthorize("hasRole('SELLER')")
    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequest request) {
        orderService.updateOrderStatus(request);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully updated Order");
    }
}
