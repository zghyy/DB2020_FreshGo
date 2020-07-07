package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanPurchase;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午1:36
 */
public class PurchaseManage {
    public void addPurchase(BeanPurchase bp) throws BusinessException {
        Connection conn = null;

        try{
            conn = DBUtil.getConnection();

            int insertId = 0;
            String sql  = "select max(pur_id) from purchase";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                insertId = rs.getInt(1)+1;
            }else {
                insertId = 1;
            }

            sql = "insert into purchase values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,insertId);
            pst.setInt(2,bp.getAdminId());
            pst.setInt(3,bp.getGoodsId());
            pst.setString(4,bp.getGoodsName());
            pst.setInt(5,bp.getPurchaseNum());
            pst.setString(6,"下单");
            if(pst.executeUpdate()==1){
                System.out.println("下单成功");
            }
            else {
                throw new BusinessException("下单失败");
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


    public void speedUp(BeanPurchase bp) throws BusinessException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String nowStatus = bp.getPurchaseStatus();
            if(nowStatus.equals("入库")){
                throw new BusinessException("商品已入库，无法加速！");
            }

            String sql = "update purchase set pur_status = ? where pur_id=?";
            PreparedStatement pst  = conn.prepareStatement(sql);
            if(nowStatus.equals("下单")){
                pst.setString(1,"在途");
                pst.setInt(2,bp.getPurchaseId());
            }
            else if(nowStatus.equals("在途")){
                pst.setString(1,"入库");
                pst.setInt(2,bp.getPurchaseId());
            }
            if(pst.executeUpdate()==1){
                JOptionPane.showMessageDialog(null, "加速成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
            }else {
                throw new BusinessException("加速失败");
            }

            if(nowStatus.equals("在途")){
                sql = "update goods_msg set g_count=? where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,bp.getPurchaseNum());
                pst.setInt(2,bp.getGoodsId());
                pst.executeUpdate();
            }
            pst.close();




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

    public List<BeanPurchase> loadPurchase() {
        Connection conn = null;
        List<BeanPurchase> res = new ArrayList<BeanPurchase>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select p.pur_id,p.a_id,a.a_name,gm.g_id,gm.g_name,p.pur_num,p.pur_status " +
                    "from purchase p,admin a,goods_msg gm " +
                    "where p.a_id = a.a_id and p.g_id=gm.g_id";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanPurchase bp = new BeanPurchase();
                bp.setPurchaseId(rs.getInt(1));
                bp.setAdminId(rs.getInt(2));
                bp.setAdminName(rs.getString(3));
                bp.setGoodsId(rs.getInt(4));
                bp.setGoodsName(rs.getString(5));
                bp.setPurchaseNum(rs.getInt(6));
                bp.setPurchaseStatus(rs.getString(7));
                res.add(bp);
            }
            rs.close();
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
        return res;

    }

}
