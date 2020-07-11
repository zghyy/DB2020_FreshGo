package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/11 上午10:19
 */
public class BeanOrder {
    private int oId;
    private int oOrder;
    private int locationId;
    private int userId;
    private double orderOldPrice;
    private double orderNewPrice;
    private int orderCoupon;
    private Date orderGettime;
    private String orderStatus;

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getoOrder() {
        return oOrder;
    }

    public void setoOrder(int oOrder) {
        this.oOrder = oOrder;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getOrderOldPrice() {
        return orderOldPrice;
    }

    public void setOrderOldPrice(double orderOldPrice) {
        this.orderOldPrice = orderOldPrice;
    }

    public double getOrderNewPrice() {
        return orderNewPrice;
    }

    public void setOrderNewPrice(double orderNewPrice) {
        this.orderNewPrice = orderNewPrice;
    }

    public int getOrderCoupon() {
        return orderCoupon;
    }

    public void setOrderCoupon(int orderCoupon) {
        this.orderCoupon = orderCoupon;
    }

    public Date getOrderGettime() {
        return orderGettime;
    }

    public void setOrderGettime(Date orderGettime) {
        this.orderGettime = orderGettime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
