package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/5 上午8:25
 */
public class FreshTypeManage {
    /**
     * 这个函数用来添加生鲜类型
     * @param freshName
     * @param freshDesc
     * @throws BusinessException
     */
    public void addFreshType(String freshName, String freshDesc) throws BusinessException {
        if ("".equals(freshName)) {
            throw new BusinessException("生鲜类型名不能为空");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh_type where type_name =?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, freshName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("该生鲜类型已经存在");
            }
            rs.close();
            pst.close();
            int insertOrder = 0;
            sql = "select max(type_order) from fresh_type ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertOrder = rs.getInt(1) + 1;
            } else {
                insertOrder = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into fresh_type(type_order,type_name,type_desc) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, insertOrder);
            pst.setString(2, freshName);
            pst.setString(3, freshDesc);
            if (pst.executeUpdate() == 1) {
                System.out.println("添加生鲜类别成功");
            } else {
                throw new BusinessException("添加生鲜类别失败");
            }
            pst.close();

        } catch (BaseException | SQLException throwables) {
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


    }

    /**
     * 这个函数用来删除生鲜类型(如果有对应生鲜类型的商品存在就无法删除)
     * @param deleteFT
     * @throws BusinessException
     */
    public void deleteFreshType(BeanFreshType deleteFT) throws BusinessException {
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "select * from goods_msg where type_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, deleteFT.getTypeId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("当前有商品属于该生鲜类型，无法删除");
            }
            rs.close();
            pst.close();

            int maxTypeOrder = 0;
            sql = "select max(type_order) from fresh_type";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                maxTypeOrder = rs.getInt(1);
            } else {
                    throw new BusinessException("数据异常");
            }

            sql = "delete from fresh_type where type_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, deleteFT.getTypeId());
            if (pst.executeUpdate() == 1) {
                System.out.println("删除成功");
            } else {
                throw new BusinessException("数据删除异常");
            }
            pst.close();

            for (int i = deleteFT.getTypeOrder() + 1; i <= maxTypeOrder; i++) {
                sql = "update fresh_type set type_Order = ? where type_Order=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, i - 1);
                pst.setInt(2, i);
                pst.executeUpdate();
                pst.close();
            }


            conn.commit();


        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    }

    /**
     * 这个函数用来加载所有的生鲜类型
     * @return
     * @throws BaseException
     */
    public List<BeanFreshType> loadFreshType() throws BaseException {
        Connection conn = null;
        List<BeanFreshType> res = new ArrayList<BeanFreshType>();

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh_type";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanFreshType bft = new BeanFreshType();
                bft.setTypeId(rs.getInt(1));
                bft.setTypeOrder(rs.getInt(2));
                bft.setTypeName(rs.getString(3));
                bft.setTypeDesc(rs.getString(4));
                res.add(bft);
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
