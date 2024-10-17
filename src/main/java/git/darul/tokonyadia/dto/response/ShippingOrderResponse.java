package git.darul.tokonyadia.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingOrderResponse {
    private String userName;
    private String phoneNumber;
    private String address;
    private String city;
}
