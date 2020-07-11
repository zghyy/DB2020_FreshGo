package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanAdmin;
import xyz.zghy.freshgo.model.BeanUsers;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;

/**
 * @author ghy
 * @date 2020/7/4 上午9:55
 */
public class UserManage {
    public BeanUsers register(String userName, String userPwd, String userPwd2
            , String userSex, int userPhone, String userCity, String userEmail) throws BusinessException {
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
            pst.setString(9, "否");
            if (pst.executeUpdate() == 1) {
                System.out.println("添加用户成功");
            } else {
                throw new BusinessException("添加用户失败");
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


        return null;
    }

    public BeanUsers login(String userName, String userPwd) throws BusinessException {
        if (userName.length() <= 0 || userName.length() >= 20) {
            throw new BusinessException("用户名应该在1——20个字符之间！");
        }
        if (userPwd.length() <= 0 || userPwd.length() >= 20) {
            throw new BusinessException("密码应该在1——20个字符之间！");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from users where u_name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString(4).equals(userPwd)) {
                    BeanUsers res = new BeanUsers();
                    res.setUserId(rs.getInt(1));
                    res.setUserName(rs.getString(2));
                    res.setUserSex(rs.getString(3));
                    res.setUserPwd(rs.getString(4));
                    res.setUserPhone(rs.getInt(5));
                    res.setUserEmail(rs.getString(6));
                    res.setUserCity(rs.getString(7));
                    res.setUserIsVip(rs.getString(9));
                    if (rs.getString(10) == null) {
                        res.setUserVipEndDate(null);
                    } else {
                        res.setUserVipEndDate(SystemUtil.SDF.parse(rs.getString(10)));
                    }
                    rs.close();
                    pst.close();
                    return res;
                } else {
                    throw new BusinessException("密码错误");
                }
            } else {
                throw new BusinessException("账号不存在");
            }


        } catch (SQLException | ParseException throwables) {
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
        return null;
    }

    public void regVip() throws BusinessException {
        if ("y".equals(SystemUtil.currentUser.getUserIsVip())) {
            throw new BusinessException("无法重复注册");
        }
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update users set u_isvip=? , u_vip_end_date=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "y");
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis() + (30 * 24 * 60 * 60 * 1000L)));
            if (pst.executeUpdate() == 1) {
                System.out.println("成功注册vip");
                SystemUtil.currentUser.setUserIsVip("y");
                JOptionPane.showMessageDialog(null, "你现在是vip啦！", "恭喜", JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new BusinessException("数据添加失败");
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
    }

    public void loadVip() {

    }

}
