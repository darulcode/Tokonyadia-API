package git.darul.tokonyadia.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {
    private String id;
    private Integer quantity;
    private String size;
    private String userAccountId;
    private String productId;
}
