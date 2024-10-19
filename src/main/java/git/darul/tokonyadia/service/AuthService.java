package git.darul.tokonyadia.service;

import git.darul.tokonyadia.dto.request.AuthRequest;
import git.darul.tokonyadia.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);
    AuthResponse refreshToken(String token);
    void logout(String accessToken);
}
