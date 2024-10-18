package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.OrderRequest;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.request.UpdateOrderRequest;
import git.darul.tokonyadia.dto.response.OrderResponse;
import git.darul.tokonyadia.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    Page<OrderResponse> getAllOrders(PagingAndShortingRequest pagingAndShortingRequest);
    OrderResponse getById(String id);
    void updateOrderStatus(UpdateOrderRequest request);
    Order getOne(String id);
}
