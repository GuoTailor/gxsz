package gyh.gxsz.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import gyh.gxsz.common.UserUtils;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.common.page.PageView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import gyh.gxsz.bean.User;
import gyh.gxsz.mapper.UserMapper;

import java.util.Collection;
import java.util.List;

/**
 * Created by gyh on 2019/8/18.
 */
@Service
public class UserService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    public User getUserInfo() {
        return selectByPrimaryKey(UserUtils.getCurrentUser().getId());
    }

    public PageView<User> getAll(PageQuery pageQuery) {
        Page<User> page = PageHelper.startPage(pageQuery);
        List<User> coupons = userMapper.getAll(pageQuery.buildSubSql());
        return PageView.build(page);
    }
    
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(User record) {
        return userMapper.insert(record);
    }

    
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User selectUserByName(String phone) {
        return userMapper.selectUserByName(phone);
    }

    public User selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }
    
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userMapper.selectUserByName(s);
    }
}
