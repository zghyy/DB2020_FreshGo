package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.CouponManage;
import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.model.BeanCoupon;
import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/13 下午7:41
 */
public class FrmSelectCoupon extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private Button btnCheck = new Button("确定使用");
    private Button btnCancel = new Button("不使用了");
    private Object tblTitle[] = {"优惠券序号", "优惠券描述", "适用金额", "减免金额", "起始日期", "结束日期"};
    private Object tblData[][];
    List<BeanCoupon> coupons = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);
    private int oId;

    private void reloadTable() {
        coupons = new CouponManage().loadCoupons();
        tblData = new Object[coupons.size()][6];
        for (int i = 0; i < coupons.size(); i++) {
            tblData[i][0] = coupons.get(i).getCouponOrder();
            tblData[i][1] = coupons.get(i).getCouponDesc();
            tblData[i][2] = coupons.get(i).getCouponAmount();
            tblData[i][3] = coupons.get(i).getCouponDiscount();
            tblData[i][4] = SystemUtil.SDF.format(coupons.get(i).getCouponStartDate());
            tblData[i][5] = SystemUtil.SDF.format(coupons.get(i).getCouponEndDate());
        }
        tblMod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmSelectCoupon(JDialog owner, String title, boolean modal,int o_id){
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.oId=o_id;

        this.getContentPane().add(toolBar,BorderLayout.SOUTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable),BorderLayout.CENTER);

        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnCheck.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource()==btnCheck){
            int i = this.dataTable.getSelectedRow();
            try{
                new CouponManage().useCoupon(this.coupons.get(i),oId);
                this.setVisible(false);
            }
            catch (BusinessException businessException){
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);

            }

        }

    }
}
