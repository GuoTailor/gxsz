package gyh.gxsz.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import gyh.gxsz.bean.UserCoupon;
import gyh.gxsz.common.UserUtils;
import gyh.gxsz.common.Util;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.common.page.PageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import gyh.gxsz.bean.Coupon;
import gyh.gxsz.mapper.CouponMapper;

import java.util.List;

/**
 * Created by gyh on 2019/8/22.
 */
@Service
public class CouponService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CouponMapper couponMapper;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private MerchantService merchantService;

    public PageView<Coupon> getAllOnEnable(PageQuery pageQuery) {
        Page<Coupon> page = PageHelper.startPage(pageQuery);
        List<Coupon> coupons = couponMapper.getAllOnEnable(pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public PageView<Coupon> getAll(PageQuery pageQuery) {
        Page<Coupon> page = PageHelper.startPage(pageQuery);
        List<Coupon> coupons = couponMapper.getAll(pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public int collect(Integer couponId) {
        int id = UserUtils.getCurrentUser().getId();
        synchronized (logger) {
            Coupon coupon = selectByPrimaryKey(couponId);
            if (coupon != null && coupon.getCount() > 0) {
                Coupon newCoupon = new Coupon();
                newCoupon.setId(couponId);
                newCoupon.setCount(coupon.getCount() - 1);
                updateByPrimaryKeySelective(newCoupon);
                UserCoupon userCoupon = new UserCoupon()
                        .setUserId(id)
                        .setCouponId(couponId)
                        .setCode(Util.getRandomInt(6));
                userCouponService.insertSelective(userCoupon);
                return 0;
            } else {
                return 1;
            }
        }
    }

    public List<Coupon> couponList() {
        Integer id = UserUtils.getCurrentUser().getId();
        return couponMapper.selectMy(id);
    }

    public int addCoupon(Coupon coupon) {
        if (coupon.getMerchantId() != null) {
            if (merchantService.selectByPrimaryKey(coupon.getMerchantId()) == null) {
                return 1;
            }
        }
        if (coupon.getMerchantId() == null) {
            return 1;
        }
        couponMapper.insertSelective(coupon);
        return 0;
    }

    public int deleteByPrimaryKey(Integer id) {
        return couponMapper.deleteByPrimaryKey(id);
    }


    public int insert(Coupon record) {
        return couponMapper.insert(record);
    }


    public int insertOrUpdate(Coupon record) {
        return couponMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Coupon record) {
        return couponMapper.insertOrUpdateSelective(record);
    }


    public int insertSelective(Coupon record) {
        return couponMapper.insertSelective(record);
    }


    public Coupon selectByPrimaryKey(Integer id) {
        return couponMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(Coupon record) {
        return couponMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Coupon record) {
        return couponMapper.updateByPrimaryKey(record);
    }

}





