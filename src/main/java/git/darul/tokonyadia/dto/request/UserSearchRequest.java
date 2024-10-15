package git.darul.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class UserSearchRequest extends PagingAndShortingRequest {
    private String name;
}
