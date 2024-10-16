package git.darul.tokonyadia.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String condition;
    private Long price;
    private Integer stock;
    private String description;
    private String statusProduct;
    private String categoryId;
    private List<ProductSizeResponse> productSize;
}
