package gyh.gxsz.mapper;

import gyh.gxsz.bean.Merchant;

import java.util.List;

/**
 * Created by gyh on 2019/8/26.
 */
public interface MerchantMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Merchant record);

    int insertOrUpdate(Merchant record);

    int insertOrUpdateSelective(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(Integer id);

    List<Merchant> getAll(String search);

    Merchant selectByAccount(String account);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);
}