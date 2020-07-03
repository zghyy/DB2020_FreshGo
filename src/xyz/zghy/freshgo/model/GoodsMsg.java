package xyz.zghy.freshgo.model;

/**
 * @author ghy
 * @date 2020/7/3 ÏÂÎç8:25
 */
public class GoodsMsg {
    private int goodsId;
    private int typeId;
    private String goodsName;
    private int goodsPrice;
    private int goodsVipPrice;
    private int goodsCount;
    private String goodsSpecifications;
    private String goodsDesc;


    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsVipPrice() {
        return goodsVipPrice;
    }

    public void setGoodsVipPrice(int goodsVipPrice) {
        this.goodsVipPrice = goodsVipPrice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setGoodsSpecifications(String goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
}
