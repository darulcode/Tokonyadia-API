package git.darul.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductRequest extends PagingAndShortingRequest {
    private String id;
    private String name;
    private Long minPrice;
    private Long maxPrice;
}
