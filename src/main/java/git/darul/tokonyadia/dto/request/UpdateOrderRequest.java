package git.darul.tokonyadia.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderRequest {
    private String orderId;
    private String status;
}
