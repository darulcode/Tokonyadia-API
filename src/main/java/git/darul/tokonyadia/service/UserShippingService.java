package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.request.UserShippingRequest;
import git.darul.tokonyadia.dto.response.UserShippingResponse;
import git.darul.tokonyadia.entity.UserShipping;
import org.springframework.data.domain.Page;

public interface UserShippingService {
    UserShippingResponse create(UserShippingRequest userShippingRequest);
    UserShippingResponse update(UserShippingRequest userShippingRequest);
    Page<UserShippingResponse> getAllUserShipping();
    UserShipping getOne(String id);
    UserShippingResponse getById(String id);
}
