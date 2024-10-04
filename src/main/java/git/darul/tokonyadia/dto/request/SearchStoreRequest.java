package git.darul.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchStoreRequest extends PagingAndShortingRequest {
    private String name;
}
