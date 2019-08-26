package gyh.gxsz.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gyh.gxsz.bean.BaseUser;
import gyh.gxsz.bean.RespBody;
import gyh.gxsz.bean.User;
import gyh.gxsz.common.Util;
import gyh.gxsz.config.token.TokenMgr;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by gyh on 2019/8/18.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.authenticationManager = authenticationManager;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        BaseUser user;
        String username = req.getParameter("username");
        String code = req.getParameter("code");
        logger.info("nmka>>>>>" + username + "  " + code);
        if (username == null || code == null) {
            throw new InternalAuthenticationServiceException("登陆失败，username:" + username + " code:" + code);
        } else {
            user = new BaseUser() {
                @Override
                public Integer getId() {
                    return null;
                }

                @Override
                public String getUsername() {
                    return username;
                }
            };
        }
        user.setRoles(Collections.singletonList((GrantedAuthority) () -> "ROLE_ADMIN"));
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(user);
        logger.info(getAuthenticationManager() == null);
        return authenticationManager.authenticate(authRequest);
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        BaseUser user = (BaseUser) auth.getDetails();
        String token = TokenMgr.createJWT(user, Constant.JWT_TTL);
        res.setContentType("application/json;charset=utf-8");
        res.addHeader("Authorization", "Bearer " + token);
        Util.addHeader(res, req);
        RespBody<String> respBean = new RespBody<>(0, token, "成功");
        res.getWriter().write(new ObjectMapper().writeValueAsString(respBean));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setContentType("application/json;charset=utf-8");
        RespBody<String> respBean = new RespBody<>(0, "登录失败!");
        Util.addHeader(response, request);
        response.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
    }

}
