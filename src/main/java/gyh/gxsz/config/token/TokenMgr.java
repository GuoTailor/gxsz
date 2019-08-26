package gyh.gxsz.config.token;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import gyh.gxsz.bean.BaseUser;
import gyh.gxsz.bean.User;
import gyh.gxsz.config.Constant;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 签发和验证token的类
 */
public class TokenMgr {

    /**
     * 创建SecretKey
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(Constant.JWT_SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 签发JWT
     */
    public static String createJWT(String id, String subject, String role, String issuer, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        System.out.println("主题" + subject + "   " + role);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)                                          // JWT_ID
                .setSubject(subject)                                // 主题
                .setAudience(role)                                  // 接受者
                //.setClaims(null)                                  // 自定义属性
                .setIssuer(issuer)                                  // 签发者
                .setNotBefore(new Date())                           // 开始时间
                .setIssuedAt(now)                                   // 签发时间
                .signWith(signatureAlgorithm, secretKey);           // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);                         // 失效时间
        }
        return builder.compact();
    }

    public static String createJWT(BaseUser user, long ttlMillis) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        if (user.getAuthorities() != null)
            claims.put("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.setExpiration(new Date(System.currentTimeMillis() + ttlMillis));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, generalKey()).compact();
    }

    /**
     * 验证JWT
     */
    public static CheckPOJO validateJWT(String jwtStr) {
        CheckPOJO checkResult = new CheckPOJO();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 解析JWT字符串
     */
    private static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
