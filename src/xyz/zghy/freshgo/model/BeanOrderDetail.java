package xyz.zghy.freshgo.model;

/**
 * @author ghy
 * @date 2020/7/9 下午12:04
 */
public class BeanOrderDetail {
    private int goodsId;
    private String goodsName;
    private int goodsCount;
    private double goodsPrice;
    private int orderId;
    private int fulldId;
    private double fdData;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFulldId() {
        return fulldId;
    }

    public void setFulldId(int fulldId) {
        this.fulldId = fulldId;
    }

    public double getFdData() {
        return fdData;
    }

    public void setFdData(double fdData) {
        this.fdData = fdData;
    }
}
