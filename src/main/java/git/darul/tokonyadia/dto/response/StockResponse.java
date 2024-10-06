package git.darul.tokonyadia.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockResponse {
    private String id;
    private String productName;
    private String storeName;
    private Integer stock;

}
