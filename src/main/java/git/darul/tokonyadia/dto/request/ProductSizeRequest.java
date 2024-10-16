package git.darul.tokonyadia.dto.request;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSizeRequest {
    private String sizeId;
    private String size;
}
