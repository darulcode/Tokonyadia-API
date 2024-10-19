package git.darul.tokonyadia.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderRequest {
    private String productId;
    private Integer quantity;
    private String size;
}
