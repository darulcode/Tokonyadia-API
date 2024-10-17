package git.darul.tokonyadia.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderResponse {
    private String id;
    private String productName;
    private Integer quantity;
    private String size;
    private Long price;
    private Long totalPrice;
}
