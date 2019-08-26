package gyh.gxsz.mapper;

import gyh.gxsz.bean.MerchantCoupon;

/**
 * Created by gyh on 2019/8/26.
 */
public interface MerchantCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MerchantCoupon record);

    int insertOrUpdate(MerchantCoupon record);

    int insertOrUpdateSelective(MerchantCoupon record);

    int insertSelective(MerchantCoupon record);

    MerchantCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MerchantCoupon record);

    int updateByPrimaryKey(MerchantCoupon record);
}