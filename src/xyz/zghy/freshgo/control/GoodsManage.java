package xyz.zghy.freshgo.control;


import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/6 上午11:07
 */
public class GoodsManage {
    public void addGoods(BeanGoodsMsg bgm) {
        Connection conn = null;
//        try{
//
//        }

    }


    public List<BeanGoodsMsg> loadGoods() throws BaseException {
        List<BeanGoodsMsg> res = new ArrayList<BeanGoodsMsg>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select g_id,type_name,g_name,g_price,g_vipprice,g_count from goods_msg gm,fresh_type ft where gm.type_id = ft.type_id";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoodsMsg bgm = new BeanGoodsMsg();
                bgm.setGoodsId(rs.getInt(1));
                bgm.setTypeName(rs.getString(2));
                bgm.setGoodsName(rs.getString(3));
                bgm.setGoodsPrice(rs.getInt(4));
                bgm.setGoodsVipPrice(rs.getInt(5));
                bgm.setGoodsCount(rs.getInt(6));
                res.add(bgm);
            }
            rs.close();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }


}
