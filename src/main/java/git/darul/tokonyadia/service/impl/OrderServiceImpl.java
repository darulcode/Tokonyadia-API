package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.PaymentStatus;
import git.darul.tokonyadia.constant.ShippingMethod;
import git.darul.tokonyadia.constant.StatusOrder;
import git.darul.tokonyadia.constant.UserType;
import git.darul.tokonyadia.dto.request.MidtransNotificationRequest;
import git.darul.tokonyadia.dto.request.OrderRequest;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.request.UpdateOrderRequest;
import git.darul.tokonyadia.dto.response.MidtransResponse;
import git.darul.tokonyadia.dto.response.OrderResponse;
import git.darul.tokonyadia.dto.response.ProductOrderResponse;
import git.darul.tokonyadia.dto.response.ShippingOrderResponse;
import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.repository.OrderRepository;
import git.darul.tokonyadia.service.OrderService;
import git.darul.tokonyadia.service.PaymentService;
import git.darul.tokonyadia.service.ProductOrderService;
import git.darul.tokonyadia.service.ShippingOrderService;
import git.darul.tokonyadia.util.AuthenticationContextUtil;
import git.darul.tokonyadia.util.ShortUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductOrderService productOrderService;
    private final ShippingOrderService shippingOrderService;
    private final PaymentService paymentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Order order = Order.builder()
                .userAccount(currentUser)
                .status(StatusOrder.PENDING)
                .shippingMethod(ShippingMethod.fromDescription(orderRequest.getShippingMethod()))
                .build();
        Order orderResult = orderRepository.saveAndFlush(order);
        List<ProductOrderResponse> productOrderResponses = productOrderService.createAll(orderRequest.getProductDetails(), orderResult);
        ShippingOrderResponse shippingOrderResponse = shippingOrderService.create(orderRequest.getUserShippingId(), orderResult);
        MidtransResponse midtransResponse = paymentService.cratePayment(orderRequest.getProductDetails(), orderResult);
        return getOrderResponse(orderResult, productOrderResponses, shippingOrderResponse, midtransResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<OrderResponse> getAllOrders(PagingAndShortingRequest request) {
        Sort sortBy = ShortUtil.parseSort(request.getSortBy());
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sortBy);
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (currentUser == null ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Page<Order> orderResult = orderRepository.findAll(pageable);
        return orderResult.map(order -> {
            ShippingOrderResponse shippingOrderResponse = shippingOrderService.findByOrder(order);
            List<ProductOrderResponse> productOrderResponses = productOrderService.findAllByOrder(order);
            MidtransResponse paymentResponse = paymentService.findPaymentByOrderId(order.getId());
            return getOrderResponse(order, productOrderResponses, shippingOrderResponse, paymentResponse);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderResponse getById(String id) {
        UserAccount currentUser = AuthenticationContextUtil.getCurrentUser();
        if (currentUser == null ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!order.getUserAccount().getId().equals(currentUser.getId()) && !currentUser.getUserType().equals(UserType.ROLE_SELLER)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        ShippingOrderResponse shippingOrderResponse = shippingOrderService.findByOrder(order);
        List<ProductOrderResponse> productOrderResponses = productOrderService.findAllByOrder(order);
        MidtransResponse paymentResponse = paymentService.findPaymentByOrderId(order.getId());
        return getOrderResponse(order, productOrderResponses, shippingOrderResponse, paymentResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(UpdateOrderRequest request) {
        Order order = getOne(request.getOrderId());
        if (order.getStatus().equals(StatusOrder.PENDING)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not paid");
        }
        order.setStatus(StatusOrder.fromDescription(request.getStatus()));
        orderRepository.save(order);
    }

    @Override
    public Order getOne(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void getNotification(MidtransNotificationRequest request) {
        Order order = getOne(request.getOrderId());
        PaymentStatus paymentStatus = paymentService.getNotification(request);

        if (paymentStatus != null && paymentStatus.equals(PaymentStatus.SETTLEMENT)) {
            order.setStatus(StatusOrder.DELIVERY);
        }

        if (paymentStatus.equals(PaymentStatus.CANCEL) || paymentStatus.equals(PaymentStatus.EXPIRE) || paymentStatus.equals(PaymentStatus.DENY))  {
            order.setStatus(StatusOrder.CANCEL);
        }


    }

    private OrderResponse getOrderResponse(Order order,List<ProductOrderResponse> productOrderResponse, ShippingOrderResponse shippingOrderResponse, MidtransResponse midtransResponse) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserAccount().getId())
                .orderDate(order.getCreatedAt())
                .shippingMethod(order.getShippingMethod().getDescription())
                .shippingOrder(shippingOrderResponse)
                .productDetails(productOrderResponse)
                .status(order.getStatus().getDescription())
                .redirectUrl(midtransResponse.getRedirectUrl())
                .build();
    }
}
