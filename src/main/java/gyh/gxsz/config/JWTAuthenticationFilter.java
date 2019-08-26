package gyh.gxsz.config;

import gyh.gxsz.bean.BaseUser;
import gyh.gxsz.bean.User;
import gyh.gxsz.common.Util;
import gyh.gxsz.config.token.CheckPOJO;
import gyh.gxsz.config.token.TokenMgr;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by gyh on 2019/8/18.
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        Util.addHeader(response, request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String authToken = authHeader.replaceFirst("Bearer ", ""); // The part after "Bearer "
            CheckPOJO checkPOJO = TokenMgr.validateJWT(authToken);
            if (checkPOJO.isSuccess()) {
                Claims claims = checkPOJO.getClaims();
                logger.info("checking authentication " + claims.get("username"));
                if (claims.size() >= 3 && SecurityContextHolder.getContext().getAuthentication() == null) {
                    BaseUser baseUser = new BaseUser() {
                        @Override
                        public Integer getId() {
                            return (int) claims.get("id");
                        }

                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                            Object role = claims.get("role");
                            return role == null ? null : ((List<String>) role).stream().map(r -> (GrantedAuthority) () -> r).collect(Collectors.toList());
                        }

                        @Override
                        public String getUsername() {
                            return claims.get("username").toString();
                        }
                    };

                    SmsCodeAuthenticationToken authentication = new SmsCodeAuthenticationToken(
                            baseUser, baseUser.getAuthorities());
                    authentication.setDetails(baseUser);
                    logger.info("authenticated user " + claims.getSubject() + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}

