package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanFullDiscountConnent;
import xyz.zghy.freshgo.model.BeanFullDiscountMsg;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/12 下午6:23
 */
public class FullDiscountConnectMannage {
    public void addFullDiscountConnect(BeanGoodsMsg bgm, BeanFullDiscountMsg bfdm) throws BusinessException {
        Connection conn = null;

        try{
            conn = DBUtil.getConnection();

            String sql  = "select * from f_g_connect where g_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,bgm.getGoodsId());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                throw new BusinessException("商品已经绑定满折信息，无法多次绑定");
            }

            int insertOrder=0;
            sql = "select max(fdc_order) from f_g_connect";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                insertOrder = rs.getInt(1)+1;
            }
            else {
                insertOrder = 1;
            }

            sql  = "insert into f_g_connect(fdc_order,g_id,g_name,fd_id,start_date,end_date) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,insertOrder);
            pst.setInt(2,bgm.getGoodsId());
            pst.setString(3,bgm.getGoodsName());
            pst.setInt(4,bfdm.getFullDiscountId());
            pst.setTimestamp(5,new Timestamp(bfdm.getFullDiscountStartDate().getTime()));
            pst.setTimestamp(6,new Timestamp(bfdm.getFullDiscountEndDate().getTime()));
            if(pst.executeUpdate()==1){
                System.out.println("商品连接成功");
            }
            else {
                throw new BusinessException("连接失败");
            }

        } catch (SQLException throwables) {
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

    public void deleteFullDiscountConnect(BeanFullDiscountConnent bfdc) throws BusinessException {
        Connection conn = null;

        try{
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            int maxOrder=0;
            String sql = "select max(fdc_order) from f_g_connect";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maxOrder = rs.getInt(1);
            }
            else {
                throw new BusinessException("数据异常");
            }
            rs.close();
            pst.close();

            sql = "delete from f_g_connect where fdc_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,bfdc.getFdcId());
            if(pst.executeUpdate()==1){
                System.out.println("商品断连成功");
            }
            else{
                throw new BusinessException("数据删除异常");
            }
            pst.close();

            for (int i = bfdc.getFdcOrder(); i <= maxOrder; i++) {
                sql = "update f_g_connect set fdc_order = ? where fdc_order=?";
                pst = conn.prepareStatement(sql);
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



    public List<BeanFullDiscountConnent> loadFullDiscountConnect() {
        Connection conn = null;
        List<BeanFullDiscountConnent> res = new ArrayList<BeanFullDiscountConnent>();

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from f_g_connect";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanFullDiscountConnent bfdc = new BeanFullDiscountConnent();
                bfdc.setFdcId(rs.getInt(1));
                bfdc.setFdcOrder(rs.getInt(2));
                bfdc.setgId(rs.getInt(3));
                bfdc.setgName(rs.getString(4));
                bfdc.setFdId(rs.getInt(5));
                bfdc.setStartDate(SystemUtil.SDF.parse(rs.getString(6)));
                bfdc.setEndDate(SystemUtil.SDF.parse(rs.getString(7)));
                res.add(bfdc);
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
