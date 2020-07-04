package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanAdmin;
import xyz.zghy.freshgo.model.BeanUsers;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ghy
 * @date 2020/7/4 上午9:55
 */
public class UserManage {
    public BeanUsers register(){
        //TODO 用户注册
        return null;
    }
    public BeanUsers login(String userName,String userPwd) throws BusinessException {
        //TODO 用户登录
        if (userName.length() <= 0 || userName.length() >= 30) {
            throw new BusinessException("用户名应该在1——30个字符之间！");
        }
        if (userPwd.length() <= 0 || userPwd.length() >= 30) {
            throw new BusinessException("密码应该在1——30个字符之间！");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select u_pwd from users where u_name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(userPwd)) {
                    BeanUsers res = new BeanUsers();
                    res.setUserName(userName);
                    return res;
                }
                else {
                    throw new BusinessException("密码错误");
                }
            } else {
                throw new BusinessException("账号不存在");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
