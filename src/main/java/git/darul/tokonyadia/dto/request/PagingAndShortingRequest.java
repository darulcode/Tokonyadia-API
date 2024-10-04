package git.darul.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingAndShortingRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
}
