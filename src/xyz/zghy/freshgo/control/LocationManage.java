package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 上午8:17
 */
public class LocationManage {
    public void addLocation(BeanLocation bl) throws BusinessException {
        Connection conn = null;

        try{
            conn = DBUtil.getConnection();
            int insertLocationOrder=0;
            String sql = "select max(locate_order) from location where u_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, SystemUtil.currentUser.getUserId());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                insertLocationOrder = rs.getInt(1)+1;
            }
            else {
                insertLocationOrder =1;
            }
            rs.close();
            pst.close();


            sql = "insert into location(u_id,locate_order,province,city,area,location_desc,linkman,phonenumber)" +
                    " values(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,SystemUtil.currentUser.getUserId());
            pst.setInt(2,insertLocationOrder);
            pst.setString(3,bl.getProvince());
            pst.setString(4,bl.getCity());
            pst.setString(5,bl.getArea());
            pst.setString(6,bl.getLocationDesc());
            pst.setString(7,bl.getLinkman());
            pst.setInt(8,bl.getPhoneNumber());
            if(pst.executeUpdate()==1){
                System.out.println("添加地址成功");
            }else {
                throw new BusinessException("添加地址失败");
            }
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
    }

    public void  deleteLocation(BeanLocation deleteLocation) throws BusinessException{

    }



    public List<BeanLocation> loadLocation() {
        Connection conn = null;
        List<BeanLocation> res = new ArrayList<BeanLocation>();

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from location";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanLocation bl = new BeanLocation();
                bl.setLocationId(rs.getInt(1));
                bl.setUserId(rs.getInt(2));
                bl.setLocateOrder(rs.getInt(3));
                bl.setProvince(rs.getString(4));
                bl.setCity(rs.getString(5));
                bl.setArea(rs.getString(6));
                bl.setLocationDesc(rs.getString(7));
                bl.setLinkman(rs.getString(8));
                bl.setPhoneNumber(rs.getInt(9));
                res.add(bl);
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
