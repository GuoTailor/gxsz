package gyh.gxsz.common;

import gyh.gxsz.bean.BaseUser;
import gyh.gxsz.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.logging.Logger;

public class UserUtils {
    private static Logger logger = Logger.getLogger(UserUtils.class.getSimpleName());
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 获取当前登陆的用户<P>
     *     注意：该方法只返回用户的id,用户名
     * @return 用户
     */
    public static BaseUser getCurrentUser() {
        BaseUser user = new BaseUser(){

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public Integer getId() {
                return null;
            }

        };
        try {
            user = (BaseUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
        }catch (ClassCastException e) {
            logger.warning("user不存在");
        }
        return user;
    }

    /**
     * 用户{@link BCryptPasswordEncoder}编码密码
     * @param password 未编码的密码
     * @return 编码后的密码
     */
    public static String passwordEncoder(String password) {
        if (password == null || password.isEmpty()) return null;
        return passwordEncoder.encode(password);
    }

    /**
     * 用{@link BCryptPasswordEncoder}验证密码
     *
     * @param password        未编码的密码
     * @param encodedPassword 已编码的密码
     * @return true:密码正确，false:密码错误
     */
    public static boolean passwordMatches(String password, String encodedPassword) {
        if (StringUtils.isEmpty(password)) return StringUtils.isEmpty(encodedPassword);
        return passwordEncoder.matches(password, encodedPassword);
    }

    /**
     *  获取客户端IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 获取请求类型
     * @param request 请求
     * @return 类型
     */
    public static String getRequestType(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestType) ? "ajax" : "json";
    }

}
