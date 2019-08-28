package gyh.gxsz.mapper;

import gyh.gxsz.bean.Order;

import java.util.List;

/**
 * Created by gyh on 2019/8/28.
 */
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertOrUpdate(Order record);

    int insertOrUpdateSelective(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> getAll(String search);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}