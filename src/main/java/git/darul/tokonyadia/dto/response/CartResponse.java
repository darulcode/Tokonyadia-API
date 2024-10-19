package git.darul.tokonyadia.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String id;
    private Integer quantity;
    private String size;
    private String userAccountId;
    private String productId;
    private String statusCart;
    private Long price;
    private Long totalPrice;
}
