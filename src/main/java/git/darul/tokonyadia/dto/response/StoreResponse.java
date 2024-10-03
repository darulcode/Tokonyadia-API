package git.darul.tokonyadia.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {
    private String id;
    private String noSiup;
    private String name;
    private String address;
    private String phoneNumber;
}
