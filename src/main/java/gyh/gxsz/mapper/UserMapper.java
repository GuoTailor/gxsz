package gyh.gxsz.mapper;

import gyh.gxsz.bean.User;

import java.util.List;

/**
 * Created by gyh on 2019/8/18.
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectUserByName(String userName);

    User selectUserByPhone(String phone);

    List<User> getAll(String search);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}