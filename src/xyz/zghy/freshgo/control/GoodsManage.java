package xyz.zghy.freshgo.control;


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
    public void addGoods(BeanGoodsMsg bgm) {
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


            int insertid = 0;
            sql = "select max(g_id) from goods_msg";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertid = rs.getInt(1) + 1;
            } else {
                insertid = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into goods_msg values(?,?,?,?,?,0,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,insertid);
            pst.setInt(2,bgm.getTypeId());
            pst.setString(3,bgm.getGoodsName());
            pst.setDouble(4,bgm.getGoodsPrice());
            pst.setDouble(5,bgm.getGoodsVipPrice());
            pst.setString(6,bgm.getGoodsSpecifications());
            pst.setString(7,bgm.getGoodsDesc());
            if(pst.executeUpdate()==1){
                System.out.println("商品添加成功");
            }
            else{
                throw new BusinessException("异常！添加失败");
            }

            pst.close();

        } catch (SQLException | BusinessException throwables) {
            throwables.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    public void deleteGoods(){
        Connection conn = null;
        
    }


    public List<BeanGoodsMsg> loadGoods() throws BaseException {
        List<BeanGoodsMsg> res = new ArrayList<BeanGoodsMsg>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select g_id,type_name,g_name,g_price,g_vipprice,g_count from goods_msg gm,fresh_type ft where gm.type_id = ft.type_id";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoodsMsg bgm = new BeanGoodsMsg();
                bgm.setGoodsId(rs.getInt(1));
                bgm.setTypeName(rs.getString(2));
                bgm.setGoodsName(rs.getString(3));
                bgm.setGoodsPrice(rs.getInt(4));
                bgm.setGoodsVipPrice(rs.getInt(5));
                bgm.setGoodsCount(rs.getInt(6));
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
