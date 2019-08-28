package gyh.gxsz.mapper;

import gyh.gxsz.bean.Coupon;import java.util.List;

/**
 * Created by gyh on 2019/8/26.
 */
public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertOrUpdate(Coupon record);

    int insertOrUpdateSelective(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    List<Coupon> getAllOnEnable(String search);

    List<Coupon> getAll(String search);

    List<Coupon> selectMy(Integer userId);
}