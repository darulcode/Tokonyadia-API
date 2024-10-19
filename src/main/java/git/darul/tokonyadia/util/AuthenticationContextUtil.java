package git.darul.tokonyadia.util;

import git.darul.tokonyadia.entity.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AuthenticationContextUtil {

    public static UserAccount getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("current user: {}", authentication.getPrincipal());
        if (authentication.getPrincipal()== "anonymousUser") return null;
        return (UserAccount) authentication.getPrincipal();
    }
}
