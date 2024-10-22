package git.darul.tokonyadia.service.impl;


import git.darul.tokonyadia.constant.Constant;
import git.darul.tokonyadia.constant.UserType;
import git.darul.tokonyadia.dto.request.UserRequest;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.repository.UserAccountRepository;
import git.darul.tokonyadia.service.UserAccountService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService, UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        UserAccount userAccount = UserAccount.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .userType(UserType.ROLE_SELLER)
                .build();
        if (userAccountRepository.findByUsername("admin").isPresent()) {return;}
        userAccountRepository.save(userAccount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAccount create(UserRequest request) {
      log.info("Creating user account {}", request);
        UserAccount userAccount = UserAccount.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .userType(UserType.ROLE_BUYER)
                .build();
        return userAccountRepository.saveAndFlush(userAccount);
    }

    @Override
    public UserAccount getOne(String id) {
        Optional<UserAccount> userAccount = userAccountRepository.findById(id);
        if (userAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.USER_NOT_FOUND);
        }
        return userAccount.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.USER_NOT_FOUND));
    }
}
