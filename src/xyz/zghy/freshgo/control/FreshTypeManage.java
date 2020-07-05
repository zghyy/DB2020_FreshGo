package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ghy
 * @date 2020/7/5 上午8:25
 */
public class FreshTypeManage {

    public void addFreshType(String freshName, String freshDesc) throws BusinessException {
        if("".equals(freshName)){
            throw new BusinessException("生鲜类型名不能为空");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh_type where freshName =?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, freshName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("该生鲜类型已经存在");
            }
            rs.close();
            pst.close();
            int insertid = 0;
            sql = "select max(type_id) from fresh_type ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertid = rs.getInt(1) + 1;
            } else {
                insertid = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into fresh_type values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, insertid);
            pst.setString(2, freshName);
            pst.setString(3, freshDesc);
            if(pst.executeUpdate()==1){
                System.out.println("添加生鲜类别成功");
            }
            else {
                throw new BusinessException("添加生鲜类别失败");
            }

        } catch (BaseException | SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
