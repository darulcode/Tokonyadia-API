package git.darul.tokonyadia.dto.response;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String user_id;
    private String userType;
}
