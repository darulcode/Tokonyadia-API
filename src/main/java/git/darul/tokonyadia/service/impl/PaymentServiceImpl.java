package git.darul.tokonyadia.service.impl;


import git.darul.tokonyadia.client.MidtransClient;
import git.darul.tokonyadia.constant.PaymentStatus;
import git.darul.tokonyadia.dto.request.MidtransNotificationRequest;
import git.darul.tokonyadia.dto.request.MidtransPaymentRequest;
import git.darul.tokonyadia.dto.request.MidtransTransactionRequest;
import git.darul.tokonyadia.dto.request.ProductOrderRequest;
import git.darul.tokonyadia.dto.response.MidtransResponse;
import git.darul.tokonyadia.entity.Order;
import git.darul.tokonyadia.entity.Payment;
import git.darul.tokonyadia.entity.Product;
import git.darul.tokonyadia.repository.PaymentRepository;
import git.darul.tokonyadia.service.PaymentService;
import git.darul.tokonyadia.service.ProductService;
import git.darul.tokonyadia.util.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MidtransClient midtransClient;
    private final ProductService productService;

    @Value("${midtrans.server.key}")
    private String MIDTRANS_SERVER_KEY;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MidtransResponse cratePayment(List<ProductOrderRequest> requests, Order order) {
        long grossAmount = 0;
        for (ProductOrderRequest productOrder : requests) {
            Product product = productService.getOne(productOrder.getProductId());
            grossAmount += productOrder.getQuantity() * product.getPrice();
        }
        MidtransPaymentRequest midtransPaymentRequest = MidtransPaymentRequest.builder()
                .enablePayments(List.of("bca_va", "gopay", "shopeepay", "other_qris"))
                .transactionDetails(MidtransTransactionRequest.builder()
                        .grossAmount(grossAmount + ((grossAmount * 100) / 12))
                        .orderId(order.getId())
                        .build())
                .build();

        log.info("order id :{}", midtransPaymentRequest.getTransactionDetails().getOrderId());
        log.info("gross amount :{}", midtransPaymentRequest.getTransactionDetails().getGrossAmount());

        String headerValue = "Basic " + Base64.getEncoder().encodeToString(MIDTRANS_SERVER_KEY.getBytes(StandardCharsets.UTF_8));
        MidtransResponse snapTransaction = midtransClient.createSnapTransaction(midtransPaymentRequest, headerValue);

        Payment payment = Payment.builder()
                .order(order)
                .status(PaymentStatus.PENDING)
                .redirectUrl(snapTransaction.getRedirectUrl())
                .amount(grossAmount + ((grossAmount * 100) / 12))
                .updateAt(LocalDateTime.now())
                .build();

        paymentRepository.saveAndFlush(payment);
        //NOTE: update status di order service

        return MidtransResponse.builder()
                .redirectUrl(snapTransaction.getRedirectUrl())
                .build();
    }

    @Override
    public MidtransResponse findPaymentByOrderId(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment order not found"));
        return MidtransResponse.builder()
                .redirectUrl(payment.getRedirectUrl())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentStatus getNotification(MidtransNotificationRequest request) {
        log.info("Start getNotification: {}", System.currentTimeMillis());
        if (!validateSignatureKey(request)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid signature key");

        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment order not found"));

        PaymentStatus newPaymentStatus = PaymentStatus.fromDescription(request.getTransactionStatus());
        payment.setStatus(newPaymentStatus);
        payment.setUpdateAt(LocalDateTime.now());


        paymentRepository.saveAndFlush(payment);
        log.info("End getNotification: {}", System.currentTimeMillis());
        return newPaymentStatus;
    }

    private boolean validateSignatureKey(MidtransNotificationRequest request) {
        String rawString = request.getOrderId() + request.getStatusCode() + request.getGrossAmount() + MIDTRANS_SERVER_KEY;
        String signatureKey = HashUtil.encryptThisString(rawString);
        return request.getSignatureKey().equalsIgnoreCase(signatureKey);
    }
}
