package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.CouponManage;
import xyz.zghy.freshgo.model.BeanCoupon;
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
 * @date 2020/7/13 上午10:07
 */
public class FrmCoupon extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加优惠券");
    private Button btnDelete = new Button("删除优惠券");
    private Object tblTitle[] = {"优惠券序号", "优惠券描述", "适用金额", "减免金额", "起始日期", "结束日期"};
    private Object tblData[][];
    List<BeanCoupon> coupons = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);

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


    public FrmCoupon(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        this.setSize(1000, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnDelete.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            FrmCouponAdd dlg = new FrmCouponAdd(this, "优惠券添加", true);
            dlg.setVisible(true);
            this.reloadTable();
        } else if (e.getSource() == btnDelete) {
            try {
                int i = this.dataTable.getSelectedRow();
                BeanCoupon bc = this.coupons.get(i);
                new CouponManage().deleteCoupons(bc);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.reloadTable();

        }

    }
}
