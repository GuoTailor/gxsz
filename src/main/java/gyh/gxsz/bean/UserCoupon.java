package gyh.gxsz.bean;

/**
 * Created by gyh on 2019/8/28.
 */
public class UserCoupon {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 优惠卷id
     */
    private Integer couponId;

    /**
     * 优惠卷编码
     */
    private String code;

    /**
     * 0:未使用；1:已使用
     */
    private String use;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public UserCoupon setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public UserCoupon setCouponId(Integer couponId) {
        this.couponId = couponId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public UserCoupon setCode(String code) {
        this.code = code;
        return this;
    }

    public String getUse() {
        return use;
    }

    public UserCoupon setUse(String use) {
        this.use = use;
        return this;
    }
}