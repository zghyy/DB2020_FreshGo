package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanMenuDetail;
import xyz.zghy.freshgo.model.BeanMenuMsg;
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
 * @date 2020/7/13 下午11:22
 */
public class MenuDetailManage {
    /**
     * 这个函数用来添加菜谱详细信息(菜谱使用的商品种类)
     * @param menuId
     */
    public void addMenuDetail(int menuId) {
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            List<BeanMenuDetail> insertDetails = SystemUtil.globalMenuDetails;
            String sql = "";
            PreparedStatement pst = null;
            ResultSet rs = null;
            for (BeanMenuDetail detail : insertDetails) {
                sql = "insert into menu_detail values(?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, menuId);
                pst.setInt(2, detail.getgId());
                pst.setString(3, detail.getgName());
                pst.executeUpdate();
                pst.close();
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

    /**
     * 这个函数用来加载所有的菜谱信息
     * @param bmm
     * @return
     */
    public List<BeanMenuDetail> loadMenuDetail(BeanMenuMsg bmm) {
        Connection conn = null;
        List<BeanMenuDetail> res = new ArrayList<BeanMenuDetail>();

        try {
            conn = DBUtil.getConnection();
            String sql = "select g_name from menu_detail where m_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, bmm.getMenuId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanMenuDetail bmd = new BeanMenuDetail();
                bmd.setgName(rs.getString(1));
                res.add(bmd);
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
