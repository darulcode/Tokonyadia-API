package git.darul.tokonyadia.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String role;
}
