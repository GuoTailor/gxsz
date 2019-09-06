package gyh.gxsz.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import gyh.gxsz.bean.Approval;
import gyh.gxsz.bean.RespBody;
import gyh.gxsz.bean.UserCoupon;
import gyh.gxsz.common.UserUtils;
import gyh.gxsz.common.page.PageQuery;
import gyh.gxsz.common.page.PageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import gyh.gxsz.bean.Audit;
import gyh.gxsz.mapper.AuditMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by gyh on 2019/8/18.
 */
@Service
public class AuditService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${DIR_MAX_NUM}")
    private int dirMaxNum = 1000;
    @Value("${HEAD_FILE_PATH}")
    private String headPath;
    @Value("${AVATAR_FILE_PATH}")
    private String filePath;

    @Resource
    private AuditMapper auditMapper;
    @Autowired
    private CouponService couponService;

    private File getFile(String fileName, int id) {
        // 文件后缀
        String suffixName = "";
        if (fileName != null)
            try {
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            } catch (StringIndexOutOfBoundsException e) {
                logger.error(e.getMessage());
            }
        int index = (int) Math.ceil(id / dirMaxNum);
        String indexPath = headPath + filePath + index + File.separatorChar + id + "-" + UUID.randomUUID() + suffixName;
        File file = new File(indexPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    public String uploadFile(MultipartFile file, String seat) throws IOException {
        int id = UserUtils.getCurrentUser().getId();
        String path = "";
        if (file != null) {
            File newFile = getFile(file.getOriginalFilename(), id);
            path = newFile.getAbsolutePath().replaceFirst(headPath, "").trim();
            file.transferTo(newFile);
        }
        Audit audit = new Audit();
        audit.setUid(id);
        switch (seat) {
            case "protocol": {
                audit.setProtocolUrl(path);
                insertOrUpdateSelective(audit);
                break;
            }
            case "student": {
                audit.setStudentUrl(path);
                insertOrUpdateSelective(audit);
                break;
            }
            case "disk1": {
                audit.setDesk1Url(path);
                insertOrUpdateSelective(audit);
                break;
            }
            case "disk2": {
                audit.setDesk2Url(path);
                insertOrUpdateSelective(audit);
                break;
            }
        }
        return path;
    }

    private void uploadFile(Audit audit, MultipartFile file, String seat) throws IOException {
        int id = UserUtils.getCurrentUser().getId();
        audit.setUid(id);
        File newFile = getFile(file.getOriginalFilename(), id);
        String path = newFile.getAbsolutePath().replaceFirst(headPath, "").trim();
        file.transferTo(newFile);
        switch (seat) {
            case "protocol": {
                audit.setProtocolUrl(path);
                break;
            }
            case "student": {
                audit.setStudentUrl(path);
                break;
            }
            case "disk1": {
                audit.setDesk1Url(path);
                break;
            }
            case "disk2": {
                audit.setDesk2Url(path);
                break;
            }
        }
    }

    public int submit(Audit audit,
                      MultipartFile protocol,
                      MultipartFile student,
                      MultipartFile[] disk) throws IOException {
        if (audit.getWay() == 2) {
            Integer id = Integer.parseInt(audit.getAccountId());
            int res = couponService.collect(id);
            if (res != 0) {
                return res;
            }
        }
        audit.setState("2");
        audit.setSubmitDate(new Date());
        int id = UserUtils.getCurrentUser().getId();
        if (protocol != null) uploadFile(audit, protocol, "protocol");
        if (student != null) uploadFile(audit, student, "student");
        if (disk != null && disk.length >= 1) uploadFile(audit, disk[0], "disk1");
        if (disk != null && disk.length >= 2) uploadFile(audit, disk[1], "disk2");
        audit.setUid(id);
        insertOrUpdateSelective(audit);
        return 0;
    }

    public Audit getAudit() {
        int id = UserUtils.getCurrentUser().getId();
        return auditMapper.selectByUserId(id);
    }

    public PageView<Approval> getAllAudit(PageQuery pageQuery){
        Page<Approval> page = PageHelper.startPage(pageQuery);
        auditMapper.getAllAudit(pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public void audit(Integer id, String state, String cause) {
        Audit audit = auditMapper.selectByPrimaryKey(id);
        Audit newAudit = new Audit();
        newAudit.setId(audit.getId());
        newAudit.setState(state);
        newAudit.setCause(cause);
        auditMapper.updateByPrimaryKeySelective(newAudit);
    }

    public int deleteByPrimaryKey(Integer id) {
        return auditMapper.deleteByPrimaryKey(id);
    }


    public int insert(Audit record) {
        return auditMapper.insert(record);
    }


    public int insertOrUpdate(Audit record) {
        return auditMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Audit record) {
        return auditMapper.insertOrUpdateSelective(record);
    }


    public int insertSelective(Audit record) {
        return auditMapper.insertSelective(record);
    }


    public Audit selectByPrimaryKey(Integer id) {
        return auditMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(Audit record) {
        return auditMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Audit record) {
        return auditMapper.updateByPrimaryKey(record);
    }

}


