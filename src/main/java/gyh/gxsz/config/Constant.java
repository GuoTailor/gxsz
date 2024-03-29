package gyh.gxsz.config;


import java.util.HashMap;
import java.util.Map;

/**
 * 经常用到的一些通用常量，基本已弃用
 */
public class Constant {
    /**
     * ================================================================
     * 数据请求返回码
     * ================================================================
     */
    /**
     * 成功
     */
    public static final int RESCODE_SUCCESS = 200;              //成功
    public static final int RESCODE_SUCCESS_DATA = 201;         //成功(有返回数据)
    public static final int RESCODE_NOEXIST = 204;              //查询结果为空

    public static final int NotModified = 304;                  //未修改的
    /**
     * 失败
     */
    public static final int RESCODE_EXCEPTION = 1002;           //请求抛出异常
    public static final int RESCODE_EXCEPTION_DATA = 400;       //错误请求
    public static final int RESCODE_NOLOGIN = 401;              //未授权
    public static final int RESCODE_NotFound = 404;             //未找到
    public static final int RESCODE_NOAUTH = 403;               //拒绝请求
    public static final int RESCODE_LOGINEXPIRE = 407;          //登录过期
    public static final int RESCODE_CONFICT = 409;              //请求的资源存在冲突
    public static final int RESCODE_PreconditionFailed = 412;   //请求的头字段没能满足其中的一个或多个
    public static final int RESCODE_Locked = 423;               //当前资源被锁定
    public static final int UnsupportedMediaType = 415;         //(不支持的媒体类型） 请求的格式不受请求页面的支持
    public static final int ExpectationFailed = 417;            //未满足期望值） 服务器未满足"期望"请求标头字段的要求
    /**
     * token（暂时没有刷新自动token机制，通过重新登录获取）
     */
    public static final int RESCODE_REFTOKEN_MSG = 1006;        //刷新TOKEN(有返回数据)
    public static final int RESCODE_REFTOKEN = 1007;            //刷新TOKEN

    public static final int JWT_ERRCODE_EXPIRE = 4001;          //Token过期
    public static final int JWT_ERRCODE_FAIL = 4002;            //验证不通过

    /**
     * jwt
     */
    public static final String JWT_ID = "5236A";                                        //jwtid
    public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";            //密匙
                                    //60秒      分    小时
    public static final long JWT_TTL = 60_000 * 60 * 24 * 7;         //超时

    /**
     * ================================================================
     * 缓存
     * ================================================================
     */
    public static final Map<String, Object> map = new HashMap<>();

}
