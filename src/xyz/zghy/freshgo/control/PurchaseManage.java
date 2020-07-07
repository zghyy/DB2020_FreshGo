package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanPurchase;
import xyz.zghy.freshgo.util.DBUtil;

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
