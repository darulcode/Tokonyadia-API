package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.ProductOrderRequest;
import git.darul.tokonyadia.dto.response.ProductOrderResponse;
import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.ProductOrder;

import java.util.List;

public interface ProductOrderService {
    List<ProductOrderResponse> createAll(List<ProductOrderRequest> requests, Order order);
    List<ProductOrderResponse> findAllByOrder(Order order);
    ProductOrderResponse findById(String id);
    ProductOrder getOne(String id);

}
