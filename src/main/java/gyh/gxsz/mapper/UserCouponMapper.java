package gyh.gxsz.mapper;

import gyh.gxsz.bean.UserCoupon;

/**
 * Created by gyh on 2019/8/22.
 */
public interface UserCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupon record);

    int insertOrUpdate(UserCoupon record);

    int insertOrUpdateSelective(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer id);

    UserCoupon selectByCode(String code);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);
}