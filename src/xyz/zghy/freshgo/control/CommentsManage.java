package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanComments;
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
 * @date 2020/7/9 下午8:09
 */
public class CommentsManage {
    public void addComments() {

    }

    public List<BeanComments> loadComments(int goodId) {
        Connection conn = null;
        List<BeanComments> res = new ArrayList<BeanComments>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select c.u_id,u.u_name,com_msg,com_date,com_star  from comments c,users u where c.u_id=u.u_id and c.g_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                BeanComments bc = new BeanComments();
                bc.setUserId(rs.getInt(1));
                bc.setUserName(rs.getString(2));
                bc.setCommentMsg(rs.getString(3));
                bc.setCommentDate(SystemUtil.SDF.parse(rs.getString(4)));
                bc.setCommentStar(rs.getInt(5));
                res.add(bc);
            }

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
