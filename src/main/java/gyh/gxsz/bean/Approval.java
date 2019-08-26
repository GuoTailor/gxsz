package gyh.gxsz.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by gyh on 2019/8/26.
 */
public class Approval {
    private Integer id;
    private String userName;
    private String houseId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pactTime;
    private Date submitDate;
    private Integer way;
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public Date getPactTime() {
        return pactTime;
    }

    public void setPactTime(Date pactTime) {
        this.pactTime = pactTime;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
