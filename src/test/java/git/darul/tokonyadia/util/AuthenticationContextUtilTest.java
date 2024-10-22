package git.darul.tokonyadia.util;


import git.darul.tokonyadia.entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationContextUtilTest {

    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetCurrentUser_WhenUserIsAnonymous() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn("anonymousUser");

        UserAccount result = AuthenticationContextUtil.getCurrentUser();

        assertNull(result, "Expected null when the current user is anonymousUser");
    }

    @Test
    public void testGetCurrentUser_WhenUserIsAuthenticated() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("testUser");
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userAccount);

        UserAccount result = AuthenticationContextUtil.getCurrentUser();

        assertEquals(userAccount, result, "Expected the authenticated user to be returned");
    }

}
