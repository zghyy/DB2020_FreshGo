package xyz.zghy.freshgo.control;


import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.util.BaseException;
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
 * @date 2020/7/6 上午11:07
 */
public class GoodsManage {
    public void addGoods(BeanGoodsMsg bgm) throws BusinessException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_msg where g_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, bgm.getGoodsName());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("该商品已存在，请不要重复添加");
            }
            rs.close();
            pst.close();


            int insertOrder = 0;
            sql = "select max(g_order) from goods_msg";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertOrder = rs.getInt(1) + 1;
            } else {
                insertOrder = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into goods_msg(type_id,g_order,g_name,g_price,g_vipprice,g_count,g_specifications,g_desc)" +
                    " values(?,?,?,?,?,0,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bgm.getTypeId());
            pst.setInt(2, insertOrder);
            pst.setString(3, bgm.getGoodsName());
            pst.setDouble(4, bgm.getGoodsPrice());
            pst.setDouble(5, bgm.getGoodsVipPrice());
            pst.setString(6, bgm.getGoodsSpecifications());
            pst.setString(7, bgm.getGoodsDesc());
            if (pst.executeUpdate() == 1) {
                System.out.println("商品添加成功");
            } else {
                throw new BusinessException("异常！添加失败");
            }

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

    }

    public void deleteGoods(BeanGoodsMsg deleteGM) throws BusinessException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);


            //TODO 针对后面与商品关联的内容，通过g_id寻找，防止误删
            String sql = "select * from f_d_connect where g_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,deleteGM.getGoodsId());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                throw new BusinessException("商品与满折信息绑定，无法删除，请先解除与满折信息的绑定！");
            }

            int maxGoodsOrder = 0;
            sql = "select max(g_order) from goods_msg";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                maxGoodsOrder = rs.getInt(1);
            } else {
                throw new BusinessException("数据异常");
            }
            rs.close();
            pst.close();


            sql = "delete from goods_msg where g_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, deleteGM.getGoodsId());
            if (pst.executeUpdate() == 1) {
                System.out.println("删除商品成功");
            } else {
                throw new BusinessException("数据删除异常");
            }
            pst.close();


            for (int i = deleteGM.getGoodsOrder() + 1; i <= maxGoodsOrder; i++) {
                sql = "update goods_msg set g_order= ? where g_order = ?";
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
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    public List<BeanGoodsMsg> loadGoods() throws BaseException {
        List<BeanGoodsMsg> res = new ArrayList<BeanGoodsMsg>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select g_order,type_name,g_name,g_price,g_vipprice,g_count,g_id " +
                    "from goods_msg gm,fresh_type ft where gm.type_id = ft.type_id";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoodsMsg bgm = new BeanGoodsMsg();
                bgm.setGoodsOrder(rs.getInt(1));
                bgm.setTypeName(rs.getString(2));
                bgm.setGoodsName(rs.getString(3));
                bgm.setGoodsPrice(rs.getInt(4));
                bgm.setGoodsVipPrice(rs.getInt(5));
                bgm.setGoodsCount(rs.getInt(6));
                bgm.setGoodsId(rs.getInt(7));
                res.add(bgm);
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

    public List<BeanGoodsMsg> loadGoodsByFresh(BeanFreshType bft) {
        List<BeanGoodsMsg> res = new ArrayList<BeanGoodsMsg>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select g_order,type_name,g_name,g_price,g_vipprice,g_count,g_id " +
                    "from goods_msg gm,fresh_type ft where gm.type_id = ft.type_id and gm.type_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,bft.getTypeId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoodsMsg bgm = new BeanGoodsMsg();
                bgm.setGoodsOrder(rs.getInt(1));
                bgm.setTypeName(rs.getString(2));
                bgm.setGoodsName(rs.getString(3));
                bgm.setGoodsPrice(rs.getInt(4));
                bgm.setGoodsVipPrice(rs.getInt(5));
                bgm.setGoodsCount(rs.getInt(6));
                bgm.setGoodsId(rs.getInt(7));
                res.add(bgm);
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
