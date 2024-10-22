package git.darul.tokonyadia.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MidtransResponse {

    @JsonProperty(value = "redirect_url")
    private String redirectUrl;
    @JsonProperty(value = "token")
    private String token;

}
