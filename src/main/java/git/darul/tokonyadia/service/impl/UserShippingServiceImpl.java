package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.PagingAndShortingRequest;
import git.darul.tokonyadia.dto.request.UserShippingRequest;
import git.darul.tokonyadia.dto.response.UserShippingResponse;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.entity.UserShipping;
import git.darul.tokonyadia.repository.UserShippingRepository;
import git.darul.tokonyadia.service.UserShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class UserShippingServiceImpl implements UserShippingService {

    private final UserShippingRepository userShippingRepository;

    @Override
    public UserShippingResponse create(UserShippingRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        UserShipping userShipping = UserShipping.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .phoneNumber(request.getPhoneNumber())
                .userAccount(userAccount)
                .build();
        userShippingRepository.saveAndFlush(userShipping);
        return getUserShippingResponse(userShipping);
    }

    @Override
    public UserShippingResponse update(UserShippingRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        UserShipping userShipping = getOne(request.getId());
        if (!userAccount.getId().equals(userShipping.getUserAccount().getId()))  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        userShipping.setName(request.getName());
        userShipping.setAddress(request.getAddress());
        userShipping.setCity(request.getCity());
        userShipping.setPhoneNumber(request.getPhoneNumber());
        UserShipping userShippingResult = userShippingRepository.save(userShipping);
        return getUserShippingResponse(userShippingResult);
    }

    @Override
    public Page<UserShippingResponse> getAllUserShipping() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(0, 10);

        Page<UserShipping> userShippingList = userShippingRepository.findAllByUserAccount(userAccount,pageable);
        return userShippingList.map(new Function<UserShipping, UserShippingResponse>() {
            @Override
            public UserShippingResponse apply(UserShipping userShipping) {
                return getUserShippingResponse(userShipping);
            }
        });
    }

    @Override
    public UserShipping getOne(String id) {
        Optional<UserShipping> userShipping = userShippingRepository.findById(id);
        if (userShipping.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Shipping not found");
        return userShipping.get();
    }

    @Override
    public UserShippingResponse getById(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        UserShipping userShipping = getOne(id);
        if (!userAccount.getId().equals(userShipping.getUserAccount().getId()))  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        return getUserShippingResponse(userShipping);
    }

    private UserShippingResponse getUserShippingResponse(UserShipping userShipping) {
        return UserShippingResponse.builder()
                .id(userShipping.getId())
                .name(userShipping.getName())
                .phoneNumber(userShipping.getPhoneNumber())
                .address(userShipping.getAddress())
                .city(userShipping.getCity())
                .build();
    }
}
