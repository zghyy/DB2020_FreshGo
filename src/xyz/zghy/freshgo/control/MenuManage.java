package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanMenuMsg;
import xyz.zghy.freshgo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/13 下午11:14
 */
public class MenuManage {
    public List<BeanMenuMsg> loadMenuMsg() {
        Connection conn = null;
        List<BeanMenuMsg> res = new ArrayList<BeanMenuMsg>();

        try {
            conn = DBUtil.getConnection();

            String sql = "select * from menu_msg";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanMenuMsg bmm = new BeanMenuMsg();
                bmm.setMenuId(rs.getInt(1));
                bmm.setMenuOrder(rs.getInt(2));
                bmm.setMenuName(rs.getString(3));
                bmm.setMenuDesc(rs.getString(4));
                bmm.setMenuStep(rs.getString(5));
                res.add(bmm);
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
