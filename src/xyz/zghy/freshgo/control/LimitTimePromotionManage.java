package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanLimitTimePromotion;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午7:20
 */
public class LimitTimePromotionManage {
    public void addLimitTimePromotion(BeanLimitTimePromotion blp) throws BusinessException {
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from LTpromotion where g_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, blp.getGoodsId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("该商品正在促销中，无法重复促销");
            }
            rs.close();
            pst.close();


            int insertOrder = 0;
            sql = "select max(ltp_order) from LTpromotion";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertOrder = rs.getInt(1) + 1;
            } else {
                insertOrder = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into LTpromotion(ltp_order,g_id,g_name,ltp_price,ltp_count,ltp_start_date,ltp_end_date)" +
                    " values(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, insertOrder);
            pst.setInt(2, blp.getGoodsId());
            pst.setString(3, blp.getGoodsName());
            pst.setInt(4, blp.getLimitTimePromotionPrice());
            pst.setInt(5, blp.getLimitTimePromotionCount());
            pst.setTimestamp(6, new Timestamp(blp.getLimitTimePromotionStartTime().getTime()));
            pst.setTimestamp(7, new Timestamp(blp.getLimitTimePromotionEndTime().getTime()));
            if (pst.executeUpdate() == 1) {
                System.out.println("促销商品信息添加成功");
            } else {
                throw new BusinessException("异常！添加失败");
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

    public void deleteLimitTimePromotion(BeanLimitTimePromotion deleteBLTP) throws BusinessException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            int maxPromotionOrder = 0;
            String sql = "select max(ltp_order) from  LTpromotion";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                maxPromotionOrder = rs.getInt(1);
            } else {
                throw new BusinessException("数据异常");
            }
            rs.close();
            pst.close();

            sql = "delete from LTpromotion where ltp_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, deleteBLTP.getLimitTimePromotionId());
            if (pst.executeUpdate() == 1) {
                System.out.println("删除促销商品成功");
            } else {
                throw new BusinessException("数据删除异常");
            }

            for (int i = deleteBLTP.getLimitTimePromotionOrder(); i <= maxPromotionOrder; i++) {
                sql = "update LTpromotion set ltp_order = ? where ltp_order=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, i - 1);
                pst.setInt(2, i);
                pst.executeUpdate();
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


    public List<BeanLimitTimePromotion> loadLimitTimePromotion() {
        List<BeanLimitTimePromotion> res = new ArrayList<BeanLimitTimePromotion>();
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from LTpromotion";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanLimitTimePromotion bltp = new BeanLimitTimePromotion();
                bltp.setLimitTimePromotionId(rs.getInt(1));
                bltp.setLimitTimePromotionOrder(rs.getInt(2));
                bltp.setGoodsId(rs.getInt(3));
                bltp.setGoodsName(rs.getString(4));
                bltp.setLimitTimePromotionPrice(rs.getInt(5));
                bltp.setLimitTimePromotionCount(rs.getInt(6));
                bltp.setLimitTimePromotionStartTime(SystemUtil.SDF.parse(rs.getString(7)));
                bltp.setLimitTimePromotionEndTime(SystemUtil.SDF.parse(rs.getString(8)));
                res.add(bltp);
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
