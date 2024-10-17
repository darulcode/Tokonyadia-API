package git.darul.tokonyadia.service;

import git.darul.tokonyadia.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount user);
    Boolean validateToken(String token);
    String getUserIdFromToken(String token);
    void blacklistAccessToken(String bearerToken);
    boolean isTokenBlacklisted(String token);
}
