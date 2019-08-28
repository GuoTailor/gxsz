package gyh.gxsz.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import gyh.gxsz.common.UserUtils;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.common.page.PageView;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import gyh.gxsz.mapper.MerchantMapper;
import gyh.gxsz.bean.Merchant;

import java.util.List;

/**
 * Created by gyh on 2019/8/26.
 */
@Service
public class MerchantService{

    @Resource
    private MerchantMapper merchantMapper;


    public PageView<Merchant> getAll(PageQuery pageQuery) {
        Page<Merchant> page = PageHelper.startPage(pageQuery);
        List<Merchant> coupons = merchantMapper.getAll(pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public void addMerchant(Merchant merchant) {
        merchant.setPassword(UserUtils.passwordEncoder(merchant.getPassword()));
        merchantMapper.insertSelective(merchant);
    }
    
    public int deleteByPrimaryKey(Integer id) {
        return merchantMapper.deleteByPrimaryKey(id);
    }

    public Merchant selectByAccount(String account) {
        return merchantMapper.selectByAccount(account);
    }
    
    public int insert(Merchant record) {
        return merchantMapper.insert(record);
    }

    
    public int insertOrUpdate(Merchant record) {
        return merchantMapper.insertOrUpdate(record);
    }

    
    public int insertOrUpdateSelective(Merchant record) {
        return merchantMapper.insertOrUpdateSelective(record);
    }

    
    public int insertSelective(Merchant record) {
        return merchantMapper.insertSelective(record);
    }

    
    public Merchant selectByPrimaryKey(Integer id) {
        return merchantMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Merchant record) {
        return merchantMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Merchant record) {
        return merchantMapper.updateByPrimaryKey(record);
    }

}
