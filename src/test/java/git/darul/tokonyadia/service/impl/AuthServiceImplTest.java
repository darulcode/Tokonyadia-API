package git.darul.tokonyadia.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import git.darul.tokonyadia.constant.UserType;
import git.darul.tokonyadia.dto.request.AuthRequest;
import git.darul.tokonyadia.dto.response.AuthResponse;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.service.JwtService;
import git.darul.tokonyadia.service.RefreshTokenService;
import git.darul.tokonyadia.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private UserAccountService userAccountService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnAuthResponse_WhenCredentialsAreValid() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("username", "password");

        Authentication authentication = mock(Authentication.class);
        UserAccount userAccount = mock(UserAccount.class);
        UserType userType = mock(UserType.class);  // Mock UserType

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userAccount);
        when(jwtService.generateToken(userAccount)).thenReturn("accessToken");
        when(refreshTokenService.createToken(userAccount.getId())).thenReturn("refreshToken");

        // Mocking getUserType() and getDescription()
        when(userAccount.getUserType()).thenReturn(userType);
        when(userType.getDescription()).thenReturn("ROLE_USER");

        // Act
        AuthResponse response = authService.login(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("ROLE_USER", response.getRole());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(userAccount);
        verify(refreshTokenService, times(1)).createToken(userAccount.getId());
    }

    @Test
    void refreshToken_ShouldReturnNewAuthResponse_WhenTokenIsValid() {
        // Arrange
        String token = "validRefreshToken";
        UserAccount userAccount = mock(UserAccount.class);
        UserType userType = mock(UserType.class);  // Mock UserType

        when(refreshTokenService.getUserIdByToken(token)).thenReturn("userId");
        when(userAccountService.getOne("userId")).thenReturn(userAccount);
        when(refreshTokenService.rotateRefreshToken("userId")).thenReturn("newRefreshToken");
        when(jwtService.generateToken(userAccount)).thenReturn("newAccessToken");

        // Mocking getUserType() and getDescription()
        when(userAccount.getUserType()).thenReturn(userType);
        when(userType.getDescription()).thenReturn("ROLE_USER");

        // Act
        AuthResponse response = authService.refreshToken(token);

        // Assert
        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals("newRefreshToken", response.getRefreshToken());
        assertEquals("ROLE_USER", response.getRole());

        verify(refreshTokenService, times(1)).getUserIdByToken(token);
        verify(userAccountService, times(1)).getOne("userId");
        verify(refreshTokenService, times(1)).rotateRefreshToken("userId");
        verify(jwtService, times(1)).generateToken(userAccount);
    }

//    @Test
//    void logout_ShouldInvokeDeleteRefreshTokenAndBlacklistAccessToken() {
//        // Arrange
//        String accessToken = "accessToken";
//        UserAccount userAccount = mock(UserAccount.class);
//
//        // Mock SecurityContext dan Authentication
//        Authentication authentication = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//
//        // Set up mock untuk SecurityContextHolder
//        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(userAccount);
//        when(userAccount.getId()).thenReturn("userId");
//
//        // Act
//        authService.logout(accessToken);
//
//        // Assert
//        verify(refreshTokenService, times(1)).deleteRefreshToken("userId");
//        verify(jwtService, times(1)).blacklistAccessToken(accessToken);
//    }
}
