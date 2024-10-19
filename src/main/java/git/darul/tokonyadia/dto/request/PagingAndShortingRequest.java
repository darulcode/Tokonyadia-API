package git.darul.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PagingAndShortingRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
}
