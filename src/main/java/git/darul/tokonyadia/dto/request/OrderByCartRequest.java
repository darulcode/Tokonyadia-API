package git.darul.tokonyadia.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderByCartRequest {
    private String idCart;
    private String shippingId;
    private String shippingMethod;
    private String paymentMethod;
}
