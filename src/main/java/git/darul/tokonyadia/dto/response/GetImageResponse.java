package git.darul.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetImageResponse {
    private byte[] image;
    private String mediaType;
}
