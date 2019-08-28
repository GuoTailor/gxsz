package gyh.gxsz.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by gyh on 2019/8/26.
 * @apiDefine Order
 * @apiParam {String} name 用户名
 * @apiParam {String} number 电话
 * @apiParam {String} studentNumber 学号
 * @apiParam {String} houseId 寝室号
 * @apiParam {String} referrerId 推荐员id
 * @apiParam {String} type A型和B型
 * @apiParam {String} status 0:待配送；1：配送完成
 * @apiParam {Date} orderTime 下单时间
 * @apiParam {String} deliverymanId 配送员id
 */
public class Order {
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 电话
     */
    private String number;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 寝室号
     */
    private String houseId;

    /**
     * 推荐员id
     */
    private String referrerId;

    /**
     * A型和B型
     */
    private String type;

    /**
     * 0:待配送；1：配送完成
     */
    private String status;

    /**
     * 下单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderTime;

    /**
     * 配送员id
     */
    private String deliverymanId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(String referrerId) {
        this.referrerId = referrerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliverymanId() {
        return deliverymanId;
    }

    public void setDeliverymanId(String deliverymanId) {
        this.deliverymanId = deliverymanId;
    }
}