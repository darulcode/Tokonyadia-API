package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.UserRequest;
import git.darul.tokonyadia.dto.request.UserSearchRequest;
import git.darul.tokonyadia.dto.response.UserResponse;
import git.darul.tokonyadia.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);
    Page<UserResponse> getAllUser(UserSearchRequest request);
    UserResponse updateUser(UserRequest userRequest);
    User getOne(String id);
}
