package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanFullDiscountMsg;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/11 下午2:38
 */
public class FullDiscountTypeMannage {
    /**
     * 这个函数用来添加满折信息
     * @param bfdm
     * @throws BusinessException
     */
    public void addFullDiscountMsg(BeanFullDiscountMsg bfdm) throws BusinessException {
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();

            int insertOrder = 0;
            String sql = "select max(fd_order) from full_discount_msg";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                insertOrder = rs.getInt(1) + 1;
            } else {
                insertOrder = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into full_discount_msg(fd_order,fd_desc,fd_needcount,fd_data,fd_start_date,fd_end_date) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, insertOrder);
            pst.setString(2, bfdm.getFullDiscountDesc());
            pst.setInt(3, bfdm.getFullDiscountNeedCount());
            pst.setDouble(4, bfdm.getFullDiscountData());
            pst.setTimestamp(5, new Timestamp(bfdm.getFullDiscountStartDate().getTime()));
            pst.setTimestamp(6, new Timestamp(bfdm.getFullDiscountEndDate().getTime()));
            if (pst.executeUpdate() == 1) {
                System.out.println("满折信息添加成功");
            } else {
                throw new BusinessException("数据插入失败");
            }


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

    /**
     * 这个函数用来加载所有的满折优惠券信息
     * @return
     */
    public List<BeanFullDiscountMsg> loadFullDiscountMsg() {
        Connection conn = null;
        List<BeanFullDiscountMsg> res = new ArrayList<BeanFullDiscountMsg>();

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from full_discount_msg";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanFullDiscountMsg bfdm = new BeanFullDiscountMsg();
                bfdm.setFullDiscountId(rs.getInt(1));
                bfdm.setFullDiscountOrder(rs.getInt(2));
                bfdm.setFullDiscountDesc(rs.getString(3));
                bfdm.setFullDiscountNeedCount(rs.getInt(4));
                bfdm.setFullDiscountData(rs.getDouble(5));
                bfdm.setFullDiscountStartDate(SystemUtil.SDF.parse(rs.getString(6)));
                bfdm.setFullDiscountEndDate(SystemUtil.SDF.parse(rs.getString(7)));
                res.add(bfdm);
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

    /**
     * 这个函数用来删除满折优惠券信息
     * @param bfdm
     * @throws BusinessException
     */
    public void deleteFullDiscountMsg(BeanFullDiscountMsg bfdm) throws BusinessException {
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            int maxFDOrder=0;
            String sql = "select max(fd_order) from full_discount_msg";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maxFDOrder=rs.getInt(1);
            }
            else {
                throw new BusinessException("数据异常");
            }
            rs.close();
            pst.close();

            sql = "delete from full_discount_msg where fd_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,bfdm.getFullDiscountId());
            if(pst.executeUpdate()==1) {
                System.out.println("数据删除成功");
            }
            else {
                throw new BusinessException("数据删除异常");
            }
            pst.close();

            for (int i = bfdm.getFullDiscountOrder()+1; i <=maxFDOrder ; i++) {
                sql = "update full_discount_msg set fd_order=? where fd_order=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,i-1);
                pst.setInt(2,i);
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
    }
}
