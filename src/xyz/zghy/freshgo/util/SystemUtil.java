package xyz.zghy.freshgo.util;

import xyz.zghy.freshgo.model.BeanAdmin;
import xyz.zghy.freshgo.model.BeanMenuDetail;
import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.model.BeanUsers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/4 下午3:33
 */

/**
 * 这个类是用来存放系统全局变量的
 */
public class SystemUtil {
    // 当前登录的账户类型，之后的用户属性判断都依靠这个
    public static String currentLoginType="";
    // 根据登录的账户类型 添加当前的管理员或者当前的用户
    public static BeanAdmin currentAdmin = null;
    public static BeanUsers currentUser = null;
    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static List<BeanOrderDetail> globalOrderDetails = new ArrayList<BeanOrderDetail>();
    public static List<BeanMenuDetail> globalMenuDetails = new ArrayList<>();
}
