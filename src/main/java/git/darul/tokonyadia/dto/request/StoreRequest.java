package git.darul.tokonyadia.dto.request;

import jakarta.persistence.Column;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreRequest {
    private String noSiup;
    private String name;
    private String address;
    private String phoneNumber;
}
