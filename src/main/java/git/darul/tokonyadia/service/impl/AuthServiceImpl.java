package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.dto.request.AuthRequest;
import git.darul.tokonyadia.dto.response.AuthResponse;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.service.AuthService;
import git.darul.tokonyadia.service.JwtService;
import git.darul.tokonyadia.service.RefreshTokenService;
import git.darul.tokonyadia.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserAccountService userAccountService;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();
        String accessToken = jwtService.generateToken(userAccount);
        String refreshToken = refreshTokenService.createToken(userAccount.getId());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(userAccount.getUserType().getDescription())
                .build();
    }

    @Override
    public AuthResponse refreshToken(String token) {
        log.info("Refresh token: {}", token);
        String userId = refreshTokenService.getUserIdByToken(token);
        log.info("Refresh token for user id {}", userId);
        UserAccount userAccount = userAccountService.getOne(userId);
        String newRefreshToken = refreshTokenService.rotateRefreshToken(userId);
        String newToken = jwtService.generateToken(userAccount);
        return AuthResponse.builder()
                .accessToken(newToken)
                .refreshToken(newRefreshToken)
                .role(userAccount.getUserType().getDescription())
                .build();
    }

    @Override
    public void logout(String accessToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        refreshTokenService.deleteRefreshToken(userAccount.getId());
        jwtService.blacklistAccessToken(accessToken);
    }
}
