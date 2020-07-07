package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanLimitTimePromotion;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午7:20
 */
public class LimitTimePromotionManage {
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
