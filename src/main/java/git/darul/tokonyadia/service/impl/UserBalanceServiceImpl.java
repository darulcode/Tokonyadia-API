package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.UserBalanceRequest;
import git.darul.tokonyadia.dto.response.UserBalanceResponse;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.entity.UserBalance;
import git.darul.tokonyadia.repository.UserBalanceRepository;
import git.darul.tokonyadia.service.UserAccountService;
import git.darul.tokonyadia.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserAccountService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBalance(UserBalanceRequest request) {
        UserAccount userAccount = userService.getOne(request.getUserId());
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBalanceWhileOrder(Long amount, UserAccount userAccount) {
        UserBalance userBalance = userBalanceRepository.findByUserAccount(userAccount).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User Account Not FOund"));
        if (userBalance.getBalance() < amount) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough balance");
        userBalance.setBalance(userBalance.getBalance() - amount);
        userBalanceRepository.save(userBalance);
    }
}
