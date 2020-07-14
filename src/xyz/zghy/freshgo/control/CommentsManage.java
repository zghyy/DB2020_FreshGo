package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanComments;
import xyz.zghy.freshgo.model.BeanOrder;
import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/9 下午8:09
 */
public class CommentsManage {
    /**
     * 这个函数用来添加评论
     * @param insertCommentMsg
     * @param insertStar
     * @param bo
     */
    public void addComments(String insertCommentMsg, int insertStar, BeanOrder bo) {
        if(insertStar>5){
            insertStar=5;
        }
        if(insertStar<0){
            insertStar=0;
        }

        List<BeanOrderDetail> beanOrderDetailList = new OrderDetailsManage().loadOrderDetails(bo.getoId());

        Connection  conn = null;

        try{
            conn = DBUtil.getConnection();
            conn .setAutoCommit(false);
            String sql = "";
            PreparedStatement pst = null;
            ResultSet rs = null;
            for (BeanOrderDetail bod: beanOrderDetailList) {
                int insertOrder= 0;
                sql = "select max(com_order) from comments where g_id = ?";
                pst =conn.prepareStatement(sql);
                pst.setInt(1,bod.getGoodsId());
                rs = pst.executeQuery();
                if(rs.next()){
                    insertOrder = rs.getInt(1)+1;
                }
                else{
                    insertOrder=1;
                }
                rs.close();
                pst.close();

                sql = "insert into comments(u_id,g_id,com_msg,com_date,com_star,com_order) values(?,?,?,?,?,?)";
                pst= conn.prepareStatement(sql);
                pst.setInt(1,SystemUtil.currentUser.getUserId());
                pst.setInt(2,bod.getGoodsId());
                pst.setString(3,insertCommentMsg);
                pst.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
                pst.setInt(5,insertStar);
                pst.setInt(6,insertOrder);
                pst.executeUpdate();
                pst.close();
            }
            
            sql = "update orders set o_status = ? where o_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,"已评价");
            pst.setInt(2,bo.getoId());
            pst.executeUpdate();
            pst.close();


            conn.commit();
        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
     * 这个函数用来根据商品Id加载评论
     * @param goodId
     * @return
     */
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
