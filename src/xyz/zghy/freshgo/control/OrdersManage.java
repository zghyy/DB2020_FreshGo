package xyz.zghy.freshgo.control;


import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.model.BeanOrder;
import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
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

            String insertLocationDetail = "";

            sql = "select l_detail from locationmsg where l_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bl.getLocationId());
            rs = pst.executeQuery();
            if (rs.next()) {
                insertLocationDetail = rs.getString(1);
            } else {
                throw new BusinessException("地址出错");
            }


            sql = "insert into orders(location_id,u_id,o_old_price,o_new_price,o_gettime,o_status,o_order,location_detail) values(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bl.getLocationId());
            pst.setInt(2, SystemUtil.currentUser.getUserId());
            pst.setDouble(3, oldPriceSum);
            pst.setDouble(4, newPriceSum);
            pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pst.setString(6, "下单");
            pst.setInt(7, insertOrderOrder);
            pst.setString(8, insertLocationDetail);
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
        SystemUtil.globalOrderDetails = new ArrayList<BeanOrderDetail>();
        return insertOrderId;
    }

    public List<BeanOrder> loadOrders() {
        Connection conn = null;
        List<BeanOrder> res = new ArrayList<BeanOrder>();

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from orders where u_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, SystemUtil.currentUser.getUserId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
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
                bo.setLocationDetail(rs.getString(10));
                res.add(bo);
            }
            rs.close();
            pst.close();

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return res;
    }

    public void speedUp(BeanOrder bo) throws BusinessException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String nowStatus = bo.getOrderStatus();
            if ("送达".equals(nowStatus) || "退货".equals(nowStatus)||"已评价".equals(nowStatus)) {
                throw new BusinessException("订单已送达或已退货，无法加速");
            }
            String sql = "update orders set o_status = ? where o_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            if ("下单".equals(nowStatus)) {
                pst.setString(1, "配送");
                pst.setInt(2, bo.getoId());
            } else if ("配送".equals(nowStatus)) {
                pst.setString(1, "送达");
                pst.setInt(2, bo.getoId());
            }
            if (pst.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "加速成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new BusinessException("加速失败");
            }

            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void backOrder(BeanOrder bo) throws BusinessException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql = "select o_status from orders where o_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, bo.getoId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if ("已评价".equals(rs.getString(1))) {
                    throw new BusinessException("商品已评价，无法退货");
                }
            } else {
                throw new BusinessException("查询出错");
            }
            rs.close();
            pst.close();
            sql = "update orders set o_status = ? where o_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "退货");
            pst.setInt(2, bo.getoId());
            if (pst.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "退货成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new BusinessException("退货失败");
            }
            pst.close();


            List<BeanOrderDetail> bods = new OrderDetailsManage().loadOrderDetails(bo.getoId());
            for (BeanOrderDetail bod : bods) {
                int currentGoods;
                sql = "select g_count from goods_msg where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, bod.getGoodsId());
                rs = pst.executeQuery();
                if (!rs.next()) {
                    throw new BusinessException("商品已被删除，无法退货");
                } else {
                    currentGoods = rs.getInt(1);
                }
                rs.close();
                pst.close();

                sql = "update goods_msg set g_count = ? where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, currentGoods + bod.getGoodsCount());
                pst.setInt(2, bod.getGoodsId());
                if (pst.executeUpdate() == 1) {
                    System.out.println("商品回退仓库成功");
                } else {
                    throw new BusinessException("退货失败");
                }
                pst.close();
            }
            conn.commit();

        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


}
