package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.UserBalanceRequest;
import git.darul.tokonyadia.dto.response.UserBalanceResponse;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.entity.UserBalance;

public interface UserBalanceService {

    void updateBalance(UserBalanceRequest amount);
    UserBalanceResponse getBalance();
    void updateBalanceWhileOrder(Long amount, UserAccount userAccount);
}
