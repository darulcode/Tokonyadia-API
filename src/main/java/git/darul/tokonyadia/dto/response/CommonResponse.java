package git.darul.tokonyadia.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private Integer Status;
    private String Message;
    private T data;
    private PagingResponse paging;
}
