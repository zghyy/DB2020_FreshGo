package xyz.zghy.freshgo.model;

/**
 * @author ghy
 * @date 2020/7/3 下午8:20
 */
public class BeanFreshType {
    private int typeId;
    private int typeOrder;


    private String typeName;
    private String typeDesc;

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public int getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(int typeOrder) {
        this.typeOrder = typeOrder;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
