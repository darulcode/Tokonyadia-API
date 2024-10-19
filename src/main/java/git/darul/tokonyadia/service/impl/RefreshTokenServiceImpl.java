package git.darul.tokonyadia.service.impl;

import git.darul.tokonyadia.service.RedisService;
import git.darul.tokonyadia.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${tokonyadia.refresh-token-expiration-in-hour}")
    private Integer REFRESH_TOKEN_EXPIRY;

    private final RedisService redisService;

    @Override
    public String createToken(String userId) {
        String refreshToken = UUID.randomUUID().toString();

        if (redisService.get("refreshToken:" + userId) != null) {
            deleteRefreshToken(userId);
        }

        redisService.save("refreshToken:" + userId, refreshToken, Duration.ofHours(REFRESH_TOKEN_EXPIRY));
        redisService.save("refreshTokenMap:" + refreshToken, userId, Duration.ofHours(REFRESH_TOKEN_EXPIRY));
        return refreshToken;
    }

    @Override
    public void deleteRefreshToken(String userId) {
        String token = redisService.get("refreshToken:" + userId);
        redisService.delete("refreshToken:" + userId);
        redisService.delete("refreshTokenMap:" + token);
    }

    @Override
    public String rotateRefreshToken(String userId) {
        deleteRefreshToken(userId);
        return createToken(userId);
    }

    @Override
    public String getUserIdByToken(String token) {
        return redisService.get("refreshTokenMap:" + token);
    }
}
