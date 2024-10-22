package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.response.ShippingOrderResponse;
import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.ShippingOrder;
import git.darul.tokonyadia.entity.UserShipping;
import git.darul.tokonyadia.repository.ShippingOrderRepository;
import git.darul.tokonyadia.service.ShippingOrderService;
import git.darul.tokonyadia.service.UserShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingOrderServiceImpl implements ShippingOrderService {
    private final ShippingOrderRepository shippingOrderRepository;
    private final UserShippingService userShippingService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ShippingOrderResponse create(String shippingUserId, Order order) {
        UserShipping userShipping = userShippingService.getOne(shippingUserId);
        ShippingOrder shippingOrder = ShippingOrder.builder()
                .order(order)
                .userName(userShipping.getName())
                .phoneNumber(userShipping.getPhoneNumber())
                .address(userShipping.getAddress())
                .city(userShipping.getCity())
                .build();

        shippingOrderRepository.saveAndFlush(shippingOrder);
        return getShippingOrderResponse(shippingOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ShippingOrderResponse findByOrder(Order order) {
        ShippingOrder shippingOrder = shippingOrderRepository.findByOrder(order).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ORDER_NOT_FOUND));
        return getShippingOrderResponse(shippingOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ShippingOrderResponse findById(String shippingOrderId) {
        ShippingOrder shippingOrder = getOne(shippingOrderId);
        return getShippingOrderResponse(shippingOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ShippingOrder getOne(String id) {
        return shippingOrderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.SHIPPING_ORDER_NOT_FOUND));
    }

    private ShippingOrderResponse getShippingOrderResponse(ShippingOrder shippingOrder) {
        return ShippingOrderResponse.builder()
                .userName(shippingOrder.getUserName())
                .phoneNumber(shippingOrder.getPhoneNumber())
                .address(shippingOrder.getAddress())
                .city(shippingOrder.getCity())
                .build();
    }
}
