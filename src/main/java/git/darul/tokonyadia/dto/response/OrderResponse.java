package git.darul.tokonyadia.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private LocalDateTime orderDate;
    private String userId;
    private String shippingMethod;
    private ShippingOrderResponse shippingOrder;
    private List<ProductOrderResponse> productDetails;
    private String status;
    private String redirectUrl;
    private String paymentMethod;
}
