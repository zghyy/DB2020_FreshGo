package xyz.zghy.freshgo.util;

import xyz.zghy.freshgo.control.AdminManage;
import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.control.UserManage;
import xyz.zghy.freshgo.model.BeanAdmin;
import xyz.zghy.freshgo.model.BeanUsers;

import java.text.SimpleDateFormat;

/**
 * @author ghy
 * @date 2020/7/4 下午3:33
 */
public class SystemUtil {
    public static String currentLoginType="";
    public static BeanAdmin currentAdmin = null;
    public static BeanUsers currentUser = null;
    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static AdminManage adminManage = new AdminManage();
    public static UserManage userManage = new UserManage();
    public static FreshTypeManage freshTypeManage = new FreshTypeManage();
}
