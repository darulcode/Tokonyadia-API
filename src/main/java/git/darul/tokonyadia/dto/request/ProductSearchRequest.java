package git.darul.tokonyadia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductSearchRequest extends PagingAndShortingRequest {
    private String query;
    private Long minPrice;
    private Long maxPrice;
}
