package git.darul.tokonyadia.controller;

import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.dto.request.MidtransNotificationRequest;
import git.darul.tokonyadia.service.OrderService;
import git.darul.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.MIDTRANS_NOTIFICATION_API)
public class PaymentController {

    private final OrderService orderService;

    @PostMapping(path = "/notifications")
    public ResponseEntity<?> handleNotification(@RequestBody Map<String, String> request) {
        MidtransNotificationRequest midtransNotificationRequest = MidtransNotificationRequest.builder()
                .transactionTime(request.get("transaction_time"))
                .orderId(request.get("order_id"))
                .grossAmount(request.get("gross_amount"))
                .statusCode(request.get("status_code"))
                .transactionStatus(request.get("transaction_status"))
                .signatureKey(request.get("signature_key"))
                .build();
        orderService.getNotification(midtransNotificationRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "OK", null);
    }

}
