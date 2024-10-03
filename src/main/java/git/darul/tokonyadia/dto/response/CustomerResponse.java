package git.darul.tokonyadia.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
}
