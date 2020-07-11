package xyz.zghy.freshgo.control;


import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.model.BeanOrder;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/9 下午2:32
 */
public class OrdersManage {
    public int createOrder(BeanLocation bl) throws BusinessException {
        int insertOrderId = 0;

        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "";
            PreparedStatement pst = null;
            ResultSet rs = null;


            sql = "select max(o_id) from orders";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertOrderId = rs.getInt(1) + 1;
            }
            rs.close();
            pst.close();

            int insertOrderOrder = 0;
            sql = "select max(o_order) from orders where u_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, SystemUtil.currentUser.getUserId());
            rs = pst.executeQuery();
            if (rs.next()) {
                insertOrderId = rs.getInt(1) + 1;
            } else {
                insertOrderId = 1;
            }

            double oldPriceSum = 0;
            double newPriceSum = 0;


            oldPriceSum = new OrderDetailsManage().getOldPrice();
            newPriceSum = new OrderDetailsManage().getNewPrice(insertOrderId);
            System.out.println(oldPriceSum);
            System.out.println(newPriceSum);

            sql = "insert into orders(location_id,u_id,o_old_price,o_new_price,o_gettime,o_status,o_order) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bl.getLocationId());
            pst.setInt(2, SystemUtil.currentUser.getUserId());
            pst.setDouble(3, oldPriceSum);
            pst.setDouble(4, newPriceSum);
            pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pst.setString(6, "下单");
            pst.setInt(7,insertOrderOrder);
            if (pst.executeUpdate() == 1) {
                System.out.println("下单成功");
            } else {
                throw new BusinessException("订单数据插入失败");
            }

            rs.close();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return insertOrderId;
    }

    public List<BeanOrder> loadOrders(){
        Connection conn = null;
        List<BeanOrder> res = new ArrayList<BeanOrder>();

        try{
            conn = DBUtil.getConnection();
            String sql = "select * from orders where u_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,SystemUtil.currentUser.getUserId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                BeanOrder bo = new BeanOrder();
                bo.setoId(rs.getInt(1));
                bo.setoOrder(rs.getInt(2));
                bo.setLocationId(rs.getInt(3));
                bo.setUserId(rs.getInt(4));
                bo.setOrderOldPrice(rs.getDouble(5));
                bo.setOrderNewPrice(rs.getDouble(6));
                bo.setOrderCoupon(rs.getInt(7));
                bo.setOrderGettime(SystemUtil.SDF.parse(rs.getString(8)));
                bo.setOrderStatus(rs.getString(9));
                res.add(bo);
            }
            rs.close();
            pst.close();

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return res;
    }

}
