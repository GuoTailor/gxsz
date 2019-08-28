package gyh.gxsz.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.Date;


/**
 * Created by gyh on 2019/8/26.
 */
public class Audit {
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String number;

    /**
     * 协议图片地址
     */
    private String protocolUrl;

    /**
     * 学生证图片地址
     */
    private String studentUrl;

    /**
     * 书桌图片地址
     */
    private String desk1Url;

    /**
     * 书桌图片地址
     */
    private String desk2Url;

    /**
     * 退款方式 1：支付宝；2：优惠卷
     */
    @Range(min = 1, max = 2, message = "退款方式 1：支付宝；2：优惠卷")
    private Integer way;

    /**
     * 账户id 如果是支付宝退款就是支付宝账号，如果是优惠卷就是优惠卷id
     */
    @NotBlank(message = "账户id不能为空")
    private String accountId;

    /**
     * 审核状态 1：未提交；2：审核中；3：审核通过；4：审核未通过
     */
    private String state;

    /**
     * 原因
     */
    private String cause;

    /**
     * 申请时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date submitDate;

    /**
     * 最后修改时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public String getStudentUrl() {
        return studentUrl;
    }

    public void setStudentUrl(String studentUrl) {
        this.studentUrl = studentUrl;
    }

    public String getDesk1Url() {
        return desk1Url;
    }

    public void setDesk1Url(String desk1Url) {
        this.desk1Url = desk1Url;
    }

    public String getDesk2Url() {
        return desk2Url;
    }

    public void setDesk2Url(String desk2Url) {
        this.desk2Url = desk2Url;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}