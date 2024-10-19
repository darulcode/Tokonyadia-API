package git.darul.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBalanceRequest {
    private String userId;
    private Long amount;
}
