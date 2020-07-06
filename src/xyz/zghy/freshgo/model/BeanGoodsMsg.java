package xyz.zghy.freshgo.model;

/**
 * @author ghy
 * @date 2020/7/3 下午8:25
 */
public class BeanGoodsMsg {
    private int goodsId;
    private int goodsOrder;
    private int typeId;
    private String typeName;
    private String goodsName;
    private double goodsPrice;
    private double goodsVipPrice;
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

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public double getGoodsVipPrice() {
        return goodsVipPrice;
    }

    public void setGoodsVipPrice(double goodsVipPrice) {
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(int goodsOrder) {
        this.goodsOrder = goodsOrder;
    }
}
