package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/4 ÉÏÎç12:05
 */
public class FullDiscountMsg {
    private int fullDiscountId;
    private String fullDiscountDesc;
    private int fullDiscountNeedCount;
    private String fullDiscountData;
    private Date fullDiscountStartDate;
    private Date fullDiscountEndDate;

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

    public String getFullDiscountData() {
        return fullDiscountData;
    }

    public void setFullDiscountData(String fullDiscountData) {
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
