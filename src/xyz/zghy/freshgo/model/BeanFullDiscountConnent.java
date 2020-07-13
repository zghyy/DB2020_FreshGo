package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/12 下午5:19
 */
public class BeanFullDiscountConnent {
    private int fdcId;
    private int fdcOrder;
    private int gId;
    private int fdId;
    private String gName;
    private Date startDate;
    private Date endDate;

    public int getFdId() {
        return fdId;
    }

    public void setFdId(int fdId) {
        this.fdId = fdId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public int getFdcOrder() {
        return fdcOrder;
    }

    public void setFdcOrder(int fdcOrder) {
        this.fdcOrder = fdcOrder;
    }

    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
