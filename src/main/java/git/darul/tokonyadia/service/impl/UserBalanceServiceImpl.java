package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.entity.UserBalance;
import git.darul.tokonyadia.repository.UserBalanceRepository;
import git.darul.tokonyadia.service.UserBalanceService;
import git.darul.tokonyadia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Override
    public void updateBalance(Long amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        Optional<UserBalance> userBalance = userBalanceRepository.findByUserAccount(userAccount);
        if (userBalance.isPresent()) userBalance.get().setBalance(userBalance.get().getBalance() + amount);
        UserBalance.builder()
                .balance(amount)
                .userAccount(userAccount)
                .build();
    }
}
