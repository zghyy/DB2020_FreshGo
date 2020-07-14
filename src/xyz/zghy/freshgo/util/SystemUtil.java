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
public class SystemUtil {
    public static String currentLoginType="";
    public static BeanAdmin currentAdmin = null;
    public static BeanUsers currentUser = null;
    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static List<BeanOrderDetail> globalOrderDetails = new ArrayList<BeanOrderDetail>();
    public static List<BeanMenuDetail> globalMenuDetails = new ArrayList<>();
}
