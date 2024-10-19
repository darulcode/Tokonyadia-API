package git.darul.tokonyadia.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
}
