package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.util.SystemUtil;

import java.util.List;

/**
 * @author ghy
 * @date 2020/7/10 上午10:40
 */
public class OrderDetailsManage {
    public double getOldPrice() {
        double oldPrice = 0;
        List<BeanOrderDetail> details = SystemUtil.globalOrderDetails;
        for (BeanOrderDetail detail : details) {


        }
        return oldPrice;
    }
}
