package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/4 ÉÏÎç12:09
 */
public class LimitTimePromotion {
    private int limitTimePromotionId;
    private int goodsId;
    private int limitTimePromotionPrice;
    private int limitTimePromotionCount;
    private Date limitTimePromotionStartTime;
    private Date limitTimePromotionEndTime;

    public int getLimitTimePromotionId() {
        return limitTimePromotionId;
    }

    public void setLimitTimePromotionId(int limitTimePromotionId) {
        this.limitTimePromotionId = limitTimePromotionId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getLimitTimePromotionPrice() {
        return limitTimePromotionPrice;
    }

    public void setLimitTimePromotionPrice(int limitTimePromotionPrice) {
        this.limitTimePromotionPrice = limitTimePromotionPrice;
    }

    public int getLimitTimePromotionCount() {
        return limitTimePromotionCount;
    }

    public void setLimitTimePromotionCount(int limitTimePromotionCount) {
        this.limitTimePromotionCount = limitTimePromotionCount;
    }

    public Date getLimitTimePromotionStartTime() {
        return limitTimePromotionStartTime;
    }

    public void setLimitTimePromotionStartTime(Date limitTimePromotionStartTime) {
        this.limitTimePromotionStartTime = limitTimePromotionStartTime;
    }

    public Date getLimitTimePromotionEndTime() {
        return limitTimePromotionEndTime;
    }

    public void setLimitTimePromotionEndTime(Date limitTimePromotionEndTime) {
        this.limitTimePromotionEndTime = limitTimePromotionEndTime;
    }
}
