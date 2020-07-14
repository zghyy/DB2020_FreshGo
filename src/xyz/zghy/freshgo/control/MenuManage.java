package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanMenuMsg;
import xyz.zghy.freshgo.util.BusinessException;
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
    /**
     * 这个函数用来创建菜谱
     * @param bmm
     * @return
     * @throws BusinessException
     */
    public int createMenu(BeanMenuMsg bmm) throws BusinessException {
        int retId = 0;
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();

            int insertOrder;
            String sql = "select max(m_order) from menu_msg";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                insertOrder = rs.getInt(1)+1;
            }
            else {
                insertOrder=1;
            }

            sql = "insert into menu_msg(m_order,m_name,m_desc) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,insertOrder);
            pst.setString(2,bmm.getMenuName());
            pst.setString(3,bmm.getMenuDesc());
            if(pst.executeUpdate()==1){
                System.out.println("菜谱新建成功");
            }
            else {
                throw new BusinessException("数据异常");
            }
            rs.close();
            pst.close();

            sql = "select max(m_id) from menu_msg";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                retId = rs.getInt(1);
            }
            else {
                throw new BusinessException("数据异常");
            }
            rs.close();
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
        return retId;
    }

    /**
     * 这个函数用来加载菜谱信息
     * @return
     */
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
