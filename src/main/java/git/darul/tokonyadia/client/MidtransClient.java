package git.darul.tokonyadia.client;

import git.darul.tokonyadia.dto.request.MidtransPaymentRequest;
import git.darul.tokonyadia.dto.response.MidtransResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "midtrans", url ="${midtrans.api.url}" )
public interface MidtransClient {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path="/snap/v1/transactions"
    )
    MidtransResponse createSnapTransaction(@RequestBody MidtransPaymentRequest request,
                                           @RequestHeader(name = HttpHeaders.AUTHORIZATION) String headerAuthorization);
}