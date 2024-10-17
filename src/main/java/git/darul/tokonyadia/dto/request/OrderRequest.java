package git.darul.tokonyadia.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String shippingMethod;
    private String userShippingId;
    private List<ProductOrderRequest> productDetails;
}
