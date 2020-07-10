package xyz.zghy.freshgo.control;


import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;

/**
 * @author ghy
 * @date 2020/7/9 下午2:32
 */
public class OrdersManage {
    public int createOrder(BeanLocation bl){
        Connection conn = null;

        try{
            conn = DBUtil.getConnection();
            String sql ="";
            PreparedStatement pst= null;
            ResultSet rs = null;

            double oldPriceSum=0;
            double newPriceSum=0;


            sql = "insert into orders(location_id,u_id,o_old_price,o_new_price,o_gettime,o_status,o_coupon) values(?,?,?,?,?,?,?)";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,bl.getLocationId());
            pst.setInt(2, SystemUtil.currentUser.getUserId());
            pst.setDouble(3,oldPriceSum);
            pst.setDouble(4,newPriceSum);
            pst.setTimestamp(5,new Timestamp(System.currentTimeMillis()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

}
