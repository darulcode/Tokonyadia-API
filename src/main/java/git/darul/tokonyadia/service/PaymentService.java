package git.darul.tokonyadia.service;

import git.darul.tokonyadia.constant.PaymentStatus;
import git.darul.tokonyadia.dto.request.MidtransNotificationRequest;
import git.darul.tokonyadia.dto.request.ProductOrderRequest;
import git.darul.tokonyadia.dto.response.MidtransResponse;
import git.darul.tokonyadia.entity.Order;

import java.util.List;

public interface PaymentService {

    MidtransResponse cratePayment(List<ProductOrderRequest> requests, Order order);
    MidtransResponse findPaymentByOrderId(String orderId);
    PaymentStatus getNotification(MidtransNotificationRequest request);
}
