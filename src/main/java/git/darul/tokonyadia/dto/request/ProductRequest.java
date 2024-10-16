package git.darul.tokonyadia.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String id;
    private String name;
    private String condition;
    private Long price;
    private Integer stock;
    private List<String> size;
    private String description;
    private String categoryId;
}
