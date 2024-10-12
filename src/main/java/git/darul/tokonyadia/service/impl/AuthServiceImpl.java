package git.darul.tokonyadia.service.impl;


import git.darul.tokonyadia.dto.request.AuthRequest;
import git.darul.tokonyadia.dto.response.AuthResponse;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.service.AuthService;
import git.darul.tokonyadia.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken(userAccount);

        return AuthResponse.builder()
                .accessToken(token)
                .role(userAccount.getRole().getDescription())
                .build();
    }
}
