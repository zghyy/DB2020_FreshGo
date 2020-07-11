package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/4 上午12:05
 */
public class BeanFullDiscountMsg {
    private int fullDiscountId;
    private int fullDiscountOrder;
    private String fullDiscountDesc;
    private int fullDiscountNeedCount;
    private double fullDiscountData;
    private Date fullDiscountStartDate;
    private Date fullDiscountEndDate;

    public int getFullDiscountOrder() {
        return fullDiscountOrder;
    }

    public void setFullDiscountOrder(int fullDiscountOrder) {
        this.fullDiscountOrder = fullDiscountOrder;
    }

    public int getFullDiscountId() {
        return fullDiscountId;
    }

    public void setFullDiscountId(int fullDiscountId) {
        this.fullDiscountId = fullDiscountId;
    }

    public String getFullDiscountDesc() {
        return fullDiscountDesc;
    }

    public void setFullDiscountDesc(String fullDiscountDesc) {
        this.fullDiscountDesc = fullDiscountDesc;
    }

    public int getFullDiscountNeedCount() {
        return fullDiscountNeedCount;
    }

    public void setFullDiscountNeedCount(int fullDiscountNeedCount) {
        this.fullDiscountNeedCount = fullDiscountNeedCount;
    }

    public double getFullDiscountData() {
        return fullDiscountData;
    }

    public void setFullDiscountData(double fullDiscountData) {
        this.fullDiscountData = fullDiscountData;
    }

    public Date getFullDiscountStartDate() {
        return fullDiscountStartDate;
    }

    public void setFullDiscountStartDate(Date fullDiscountStartDate) {
        this.fullDiscountStartDate = fullDiscountStartDate;
    }

    public Date getFullDiscountEndDate() {
        return fullDiscountEndDate;
    }

    public void setFullDiscountEndDate(Date fullDiscountEndDate) {
        this.fullDiscountEndDate = fullDiscountEndDate;
    }
}
