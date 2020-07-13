package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanCoupon;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/13 上午10:10
 */
public class CouponManage {
    public void addCoupons(BeanCoupon bc) throws BusinessException {
        Connection conn = null;


        try {
            conn = DBUtil.getConnection();
            int insertOrder = 0;
            String sql = "select max(cp_order) from coupon";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                insertOrder = rs.getInt(1) + 1;
            } else {
                insertOrder = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into coupon(cp_order,cp_desc,cp_amount,cp_discount,cp_start_date,cp_end_date) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, insertOrder);
            pst.setString(2, bc.getCouponDesc());
            pst.setDouble(3, bc.getCouponAmount());
            pst.setDouble(4, bc.getCouponDiscount());
            pst.setTimestamp(5, new Timestamp(bc.getCouponStartDate().getTime()));
            pst.setTimestamp(6, new Timestamp(bc.getCouponEndDate().getTime()));
            if (pst.executeUpdate() == 1) {
                System.out.println("优惠券添加成功");
            } else {
                throw new BusinessException("数据添加异常");
            }
            pst.close();

        } catch (SQLException throwables) {
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

    public void deleteCoupons(BeanCoupon bc) throws BusinessException {
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            int maxOrder = 0;
            String sql = "select max(cp_order) from coupon";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                maxOrder = rs.getInt(1);
            } else {
                throw new BusinessException("数据查询异常");
            }
            rs.close();
            pst.close();

            sql = "delete from coupon where cp_order=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bc.getCouponId());
            if (pst.executeUpdate() == 1) {
                System.out.println("优惠券删除成功");
            }
            pst.close();

            for (int i = bc.getCouponOrder(); i <= maxOrder; i++) {
                sql = "update coupon set cp_order = ? where cp_order=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, i - 1);
                pst.setInt(2, i);
                pst.executeUpdate();
            }
            pst.close();
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


    public List<BeanCoupon> loadCoupons() {
        List<BeanCoupon> res = new ArrayList<BeanCoupon>();
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanCoupon bc = new BeanCoupon();
                bc.setCouponId(rs.getInt(1));
                bc.setCouponOrder(rs.getInt(2));
                bc.setCouponDesc(rs.getString(3));
                bc.setCouponAmount(rs.getDouble(4));
                bc.setCouponDiscount(rs.getDouble(5));
                bc.setCouponStartDate(SystemUtil.SDF.parse(rs.getString(6)));
                bc.setCouponEndDate(SystemUtil.SDF.parse(rs.getString(7)));
                res.add(bc);
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
}
