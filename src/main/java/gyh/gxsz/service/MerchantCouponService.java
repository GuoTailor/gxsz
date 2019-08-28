package gyh.gxsz.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import gyh.gxsz.bean.Coupon;
import gyh.gxsz.bean.User;
import gyh.gxsz.bean.UserCoupon;
import gyh.gxsz.common.UserUtils;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.common.page.PageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import gyh.gxsz.mapper.MerchantCouponMapper;
import gyh.gxsz.bean.MerchantCoupon;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gyh on 2019/8/26.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class MerchantCouponService {

    @Resource
    private MerchantCouponMapper merchantCouponMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private CouponService couponService;

    public int scanCode(String code) {
        UserCoupon userCoupon = userCouponService.selectByCode(code);
        // 没有拥有该优惠券
        if (userCoupon == null) {
            return 1;
        }
        // 优惠券不存在
        Coupon coupon = couponService.selectByPrimaryKey(userCoupon.getCouponId());
        if (coupon == null) {
            return 2;
        }
        // 优惠券不是本商家的
        Integer id = UserUtils.getCurrentUser().getId();
        if (!coupon.getMerchantId().equals(id)) {
            return 3;
        }
        // 用户不存在
        User user = userService.selectByPrimaryKey(userCoupon.getUserId());
        if (user == null) {
            return 4;
        }
        userCoupon.setUse("1");
        userCouponService.updateByPrimaryKeySelective(userCoupon);
        MerchantCoupon mc = new MerchantCoupon();
        mc.setmId(id);
        mc.setName(user.getUserName());
        mc.setNumber(user.getPhone());
        mc.setDenomination(coupon.getDenomination());
        insertSelective(mc);
        return 0;
    }

    public PageView<MerchantCoupon> getAllUseCoupon(PageQuery pageQuery){
        Page<MerchantCoupon> page = PageHelper.startPage(pageQuery);
        merchantCouponMapper.getAll(pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public int deleteByPrimaryKey(Integer id) {
        return merchantCouponMapper.deleteByPrimaryKey(id);
    }


    public int insert(MerchantCoupon record) {
        return merchantCouponMapper.insert(record);
    }


    public int insertOrUpdate(MerchantCoupon record) {
        return merchantCouponMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(MerchantCoupon record) {
        return merchantCouponMapper.insertOrUpdateSelective(record);
    }


    public int insertSelective(MerchantCoupon record) {
        return merchantCouponMapper.insertSelective(record);
    }


    public MerchantCoupon selectByPrimaryKey(Integer id) {
        return merchantCouponMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(MerchantCoupon record) {
        return merchantCouponMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(MerchantCoupon record) {
        return merchantCouponMapper.updateByPrimaryKey(record);
    }

}


