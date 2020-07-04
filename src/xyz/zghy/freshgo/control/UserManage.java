package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanAdmin;
import xyz.zghy.freshgo.model.BeanUsers;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;

import java.sql.*;

/**
 * @author ghy
 * @date 2020/7/4 上午9:55
 */
public class UserManage {
    public BeanUsers register(String userName, String userPwd, String userPwd2
            , String userSex, int userPhone, String userCity, String userEmail) throws BusinessException {
        //TODO 用户注册
        if (userName.length() <= 0 || userName.length() >= 20) {
            throw new BusinessException("用户名应该在1——20个字符之间！");
        }
        if (userPwd.length() <= 0 || userPwd.length() >= 20) {
            throw new BusinessException("密码应该在1——20个字符之间！");
        }
        if (!userPwd.equals(userPwd2)) {
            throw new BusinessException("两次输入的密码不一致");
        }

        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from users where u_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("账号以存在，请更换用户名");
            }
            rs.close();
            pst.close();

            int insertUserId = 1;
            sql = "select max(u_id) from users ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertUserId = rs.getInt(1) + 1;
            } else {
                insertUserId = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into users(u_id,u_name,u_sex,u_pwd,u_phone,u_email,u_city,u_createdate,u_isvip) values(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, insertUserId);
            pst.setString(2, userName);
            pst.setString(3, userSex);
            pst.setString(4, userPwd);
            pst.setInt(5, userPhone);
            pst.setString(6, userEmail);
            pst.setString(7, userCity);
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.setString(9,"否");
            if (pst.executeUpdate() == 1) {
                System.out.println("添加成功");
            } else {
                throw new BusinessException("添加数据失败");
            }
            rs.close();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }


        return null;
    }

    public BeanUsers login(String userName, String userPwd) throws BusinessException {
        //TODO 用户登录
        if (userName.length() <= 0 || userName.length() >= 20) {
            throw new BusinessException("用户名应该在1——20个字符之间！");
        }
        if (userPwd.length() <= 0 || userPwd.length() >= 20) {
            throw new BusinessException("密码应该在1——20个字符之间！");
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
                    rs.close();
                    pst.close();
                    BeanUsers res = new BeanUsers();
                    res.setUserName(userName);
                    return res;
                } else {
                    throw new BusinessException("密码错误");
                }
            } else {
                throw new BusinessException("账号不存在");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
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
