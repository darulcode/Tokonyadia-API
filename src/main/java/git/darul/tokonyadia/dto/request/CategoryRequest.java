package git.darul.tokonyadia.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private String id;
    private String name;
}
