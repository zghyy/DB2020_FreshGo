package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.model.BeanRecommand;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/14 上午10:06
 */
public class RecommandManage {
    public List<BeanRecommand> loadRecommand() throws BusinessException {
        Connection conn = null;
        List<BeanRecommand> res = new ArrayList<BeanRecommand>();

        try {
            conn = DBUtil.getConnection();
            int oID = 0;
            String sql = "select o_id from orders where u_id = ? ORDER BY o_id DESC LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, SystemUtil.currentUser.getUserId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                oID = rs.getInt(1);
            } else {
                throw new BusinessException("您还未下过单，无法生成推荐商品");
            }
            rs.close();
            pst.close();

            List<BeanOrderDetail> orderDetails = new ArrayList<BeanOrderDetail>();
            sql = "select goods_id from orders_detail where order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,oID);
            rs = pst.executeQuery();
            while (rs.next()){
                BeanOrderDetail bod = new BeanOrderDetail();
                bod.setGoodsId(rs.getInt(1));
                orderDetails.add(bod);
            }
            rs.close();
            pst.close();

            for (BeanOrderDetail detail: orderDetails) {
                sql = "select * from menugoodsdetail where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,detail.getGoodsId());
                rs = pst.executeQuery();
                while (rs.next()){
                    BeanRecommand br = new BeanRecommand();
                    br.setmId(rs.getInt(1));
                    br.setmName(rs.getString(2));
                    br.setDesc(rs.getString(3));
                    br.setgId(rs.getInt(4));
                    br.setgName(rs.getString(5));
                    res.add(br);
                }
                rs.close();
                pst.close();
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
        return res;
    }
}
