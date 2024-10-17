package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.OrderRequest;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    Page<OrderResponse> getAllOrders(PagingAndShortingRequest pagingAndShortingRequest);
    OrderResponse getById(String id);
}
