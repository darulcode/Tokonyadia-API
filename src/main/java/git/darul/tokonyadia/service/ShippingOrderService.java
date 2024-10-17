package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.response.ShippingOrderResponse;
import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.ShippingOrder;

public interface ShippingOrderService {
    ShippingOrderResponse create(String shippingUserId, Order order);
    ShippingOrderResponse findByOrder(Order order);
    ShippingOrderResponse findById(String shippingOrderId);
    ShippingOrder getOne(String id);
}
