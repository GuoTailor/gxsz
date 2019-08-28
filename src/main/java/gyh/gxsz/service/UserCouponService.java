package gyh.gxsz.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import gyh.gxsz.mapper.UserCouponMapper;
import gyh.gxsz.bean.UserCoupon;

/**
 * Created by gyh on 2019/8/22.
 */
@Service
public class UserCouponService {

    @Resource
    private UserCouponMapper userCouponMapper;


    public int deleteByPrimaryKey(Integer id) {
        return userCouponMapper.deleteByPrimaryKey(id);
    }


    public int insert(UserCoupon record) {
        return userCouponMapper.insert(record);
    }


    public int insertOrUpdate(UserCoupon record) {
        return userCouponMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(UserCoupon record) {
        return userCouponMapper.insertOrUpdateSelective(record);
    }


    public int insertSelective(UserCoupon record) {
        return userCouponMapper.insertSelective(record);
    }


    public UserCoupon selectByPrimaryKey(Integer id) {
        return userCouponMapper.selectByPrimaryKey(id);
    }

    public UserCoupon selectByCode(String code) {
        return userCouponMapper.selectByCode(code);
    }

    public int updateByPrimaryKeySelective(UserCoupon record) {
        return userCouponMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(UserCoupon record) {
        return userCouponMapper.updateByPrimaryKey(record);
    }

}

