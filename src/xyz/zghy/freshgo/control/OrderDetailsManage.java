package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/10 上午10:40
 */
public class OrderDetailsManage {
    public double getOldPrice() throws BusinessException {
        double oldPrice = 0;
        List<BeanOrderDetail> details = SystemUtil.globalOrderDetails;
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "";
            PreparedStatement pst = null;
            ResultSet rs = null;
            for (BeanOrderDetail detail : details) {
                sql = "select g_count from goods_msg where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, detail.getGoodsId());
                rs = pst.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(1) < detail.getGoodsCount()) {
                        throw new BusinessException("商品库存不足，请重新选择商品");
                    }
                }
                rs.close();
                pst.close();


                oldPrice += detail.getGoodsPrice() * detail.getGoodsCount();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return oldPrice;
    }

    public double getNewPrice(int orderId) {
        double newPrice = 0;
        List<BeanOrderDetail> details = SystemUtil.globalOrderDetails;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "";
            PreparedStatement pst = null;
            ResultSet rs = null;


            for (BeanOrderDetail detail : details) {
                //TODO 先查找限时促销商品，如果有就直接用限时促销商品的价格(同时判断促销数量，超过部分按原价)
                sql = "select ltp_price,ltp_count,ltp_end_date from LTpromotion  where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, detail.getGoodsId());
                rs = pst.executeQuery();
                if (rs.next()) {
                    if (SystemUtil.SDF.parse(rs.getString(3)).getTime() > System.currentTimeMillis()){
                        if(rs.getInt(2)> detail.getGoodsCount()){
                            newPrice+=rs.getInt(1)*detail.getGoodsCount();
                        }
                        else {
                            newPrice+=rs.getInt(1)*rs.getInt(2);
                            newPrice+=detail.getGoodsPrice()*(detail.getGoodsCount()-rs.getInt(2));
                        }
                    }
                    continue;
                }
                rs.close();
                pst.close();


                //TODO 创建视图 判断当前数据是否满足满折优惠券 满折的话需要加上满折信息与id，后期更新details
//            if (){
//
//            }
//            else{
//
//            }
                newPrice += detail.getGoodsPrice() * detail.getGoodsCount();
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return newPrice;
    }
}
