package git.darul.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest extends PagingAndShortingRequest {
    private String productId;
    private String storeId;
    private Integer stock;
}
