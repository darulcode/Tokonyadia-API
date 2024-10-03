package git.darul.tokonyadia.util;

import git.darul.tokonyadia.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T>ResponseEntity<CommonResponse<T>> buildResponseEntity(HttpStatus httpStatus, String message, T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>(httpStatus.value(), message, data);
        return ResponseEntity.status(httpStatus).body(commonResponse);
    }
}
