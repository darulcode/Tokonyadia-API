package git.darul.tokonyadia.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShippingResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
}
