package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.UserBalanceRequest;
import git.darul.tokonyadia.dto.response.UserBalanceResponse;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.entity.UserBalance;
import git.darul.tokonyadia.repository.UserBalanceRepository;
import git.darul.tokonyadia.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBalance(UserBalanceRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        Optional<UserBalance> userBalance = userBalanceRepository.findByUserAccount(userAccount);
        if (userBalance.isPresent()) {
            userBalance.get().setBalance(userBalance.get().getBalance() + request.getAmount());
            userBalanceRepository.save(userBalance.get());
            return;
        }
        userBalanceRepository.save(UserBalance.builder()
                .balance(request.getAmount())
                .userAccount(userAccount)
                .build());
    }

    @Override
    public UserBalanceResponse getBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        log.info("getBalance : {}", userAccount.getId());
        Optional<UserBalance> userBalance = userBalanceRepository.findByUserAccount(userAccount);
        if (userBalance.isPresent()) {
            return UserBalanceResponse.builder().balance(userBalance.get().getBalance()).build();
        }
        UserBalance userBalanceResult = userBalanceRepository.save(UserBalance.builder()
                .balance(0L)
                .userAccount(userAccount)
                .build());
        return UserBalanceResponse.builder().balance(userBalanceResult.getBalance()).build();
    }
}
