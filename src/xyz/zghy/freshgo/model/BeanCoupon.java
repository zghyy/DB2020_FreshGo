package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/4 上午12:02
 */
public class BeanCoupon {
    private int couponId;
    private int couponOrder;
    private String couponDesc;
    private double couponAmount;
    private double couponDiscount;
    private Date couponStartDate;
    private Date couponEndDate;

    public int getCouponOrder() {
        return couponOrder;
    }

    public void setCouponOrder(int couponOrder) {
        this.couponOrder = couponOrder;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public double getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public Date getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(Date couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public Date getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(Date couponEndDate) {
        this.couponEndDate = couponEndDate;
    }
}
