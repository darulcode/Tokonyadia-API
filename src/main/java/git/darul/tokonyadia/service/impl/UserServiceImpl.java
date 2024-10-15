package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.constant.UserType;
import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.request.UserRequest;
import git.darul.tokonyadia.dto.request.UserSearchRequest;
import git.darul.tokonyadia.dto.response.UserResponse;
import git.darul.tokonyadia.entity.User;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.repository.UserRepository;
import git.darul.tokonyadia.service.UserAccountService;
import git.darul.tokonyadia.service.UserService;
import git.darul.tokonyadia.spesification.UserSpecification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse createUser(UserRequest request) {
        UserAccount userAccount = userAccountService.create(request);
        User user = User.builder()
                .userAccount(userAccount)
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
        userRepository.saveAndFlush(user);
        return getUserResponse(user);

    }

    @Override
    public Page<UserResponse> getAllUser(UserSearchRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        if (userAccount.getUserType().equals(UserType.ROLE_BUYER))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        Specification<User> userSpecification = UserSpecification.specificationUser(request);
        Page<User> userResult = userRepository.findAll(userSpecification, pageable);
        return userResult.map(new Function<User, UserResponse>() {
            @Override
            public UserResponse apply(User user) {
                return getUserResponse(user);
            }
        });
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        return  null;
    }

    @Override
    public User getOne(String id) {
        Optional<User> user = userRepository.findById(id);
        // TODO: Membenarkan pesan error dan error handling
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        return user.get();
    }

    private UserResponse getUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .user_id(user.getUserAccount().getId())
                .userType(user.getUserAccount().getUserType().getDescription())
                .build();
    }
}
