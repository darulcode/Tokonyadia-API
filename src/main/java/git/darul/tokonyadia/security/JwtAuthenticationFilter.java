package git.darul.tokonyadia.security;

import git.darul.tokonyadia.entity.User;
import git.darul.tokonyadia.entity.UserAccount;
import git.darul.tokonyadia.service.JwtService;
import git.darul.tokonyadia.service.UserAccountService;
import git.darul.tokonyadia.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserAccountService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // TODO: Verikasi token yang dibawa oleh client/error
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = parseToken(header);

            //TODO: Ambil id
            String userId = jwtService.getUserIdFromToken(token);

            // TODO: get Useraccount by id
            UserAccount user = userService.getOne(userId);

            //TODO: set authentication ke securityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {
            log.error("Cannot set user authentitaction : {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    public String parseToken(String token) {
        if (!token.startsWith("Bearer ")) throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        return token.substring(7);
    }
}
