package gyh.gxsz.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import gyh.gxsz.bean.Order;
import gyh.gxsz.mapper.OrderMapper;

/**
 * Created by gyh on 2019/8/26.
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;


    public int deleteByPrimaryKey(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }


    public int insert(Order record) {
        return orderMapper.insert(record);
    }


    public int insertOrUpdate(Order record) {
        return orderMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Order record) {
        return orderMapper.insertOrUpdateSelective(record);
    }


    public int insertSelective(Order record) {
        return orderMapper.insertSelective(record);
    }


    public Order selectByPrimaryKey(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Order record) {
        return orderMapper.updateByPrimaryKey(record);
    }

}

