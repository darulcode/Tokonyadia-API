package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.UserRequest;
import git.darul.tokonyadia.entity.UserAccount;

public interface UserAccountService {

    UserAccount create(UserRequest request);
    UserAccount getOne(String id);
}
