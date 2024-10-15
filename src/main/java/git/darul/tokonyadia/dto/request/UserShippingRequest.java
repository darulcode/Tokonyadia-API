package git.darul.tokonyadia.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShippingRequest {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
}
