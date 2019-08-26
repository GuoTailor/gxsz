package gyh.gxsz.mapper;

import gyh.gxsz.bean.Approval;
import gyh.gxsz.bean.Audit;

import java.util.List;

/**
 * Created by gyh on 2019/8/26.
 */
public interface AuditMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Audit record);

    int insertOrUpdate(Audit record);

    int insertOrUpdateSelective(Audit record);

    int insertSelective(Audit record);

    Audit selectByPrimaryKey(Integer id);

    List<Approval> getAllAudit(String search);

    int updateByPrimaryKeySelective(Audit record);

    int updateByPrimaryKey(Audit record);

    Audit selectByUserId(Integer userId);
}