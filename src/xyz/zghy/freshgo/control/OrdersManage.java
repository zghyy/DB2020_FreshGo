package xyz.zghy.freshgo.control;


import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;

/**
 * @author ghy
 * @date 2020/7/9 下午2:32
 */
public class OrdersManage {
    public int createOrder(BeanLocation bl) throws BusinessException {
        int insertOrderId=0;

        Connection conn = null;

        try{
            conn = DBUtil.getConnection();
            String sql ="";
            PreparedStatement pst= null;
            ResultSet rs = null;


            sql = "select max(o_id) from orders";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                insertOrderId=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            double oldPriceSum=0;
            double newPriceSum=0;


            oldPriceSum = new OrderDetailsManage().getOldPrice();
            newPriceSum = new OrderDetailsManage().getNewPrice(insertOrderId);
            System.out.println(oldPriceSum);
            System.out.println(newPriceSum);

            sql = "insert into orders(location_id,u_id,o_old_price,o_new_price,o_gettime,o_status) values(?,?,?,?,?,?)";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,bl.getLocationId());
            pst.setInt(2, SystemUtil.currentUser.getUserId());
            pst.setDouble(3,oldPriceSum);
            pst.setDouble(4,newPriceSum);
            pst.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
            pst.setString(6,"下单");
            if(pst.executeUpdate()==1){
                System.out.println("下单成功");
            }
            else {
                throw new BusinessException("订单数据插入失败");
            }

            rs.close();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return insertOrderId;
    }

}
