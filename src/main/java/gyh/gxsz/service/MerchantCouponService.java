package gyh.gxsz.service;

import gyh.gxsz.bean.UserCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import gyh.gxsz.mapper.MerchantCouponMapper;
import gyh.gxsz.bean.MerchantCoupon;

/**
 * Created by gyh on 2019/8/26.
 */
@Service
public class MerchantCouponService {

    @Resource
    private MerchantCouponMapper merchantCouponMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserCouponService userCouponService;

    public int scanCode(String code) {
        UserCoupon userCoupon = userCouponService.selectByCode(code);
        if (userCoupon == null) {
            return 1;
        }

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


