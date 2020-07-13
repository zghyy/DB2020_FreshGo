package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.LocationManage;
import xyz.zghy.freshgo.control.OrdersManage;
import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/9 下午6:37
 */
public class FrmSelectInfo extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnCheck = new Button("确认");
    private Button btnDCanel = new Button("取消");
    private Object tblTitle[] = {"地址序号", "省", "市", "区", "详细地址", "联系人", "联系人手机号"};
    private Object tblData[][];
    List<BeanLocation> locations = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);

    private void reloadTable() {
        locations = new LocationManage().loadLocation();
        tblData = new Object[locations.size()][7];
        for (int i = 0; i < locations.size(); i++) {
            tblData[i][0] = locations.get(i).getLocateOrder();
            tblData[i][1] = locations.get(i).getProvince();
            tblData[i][2] = locations.get(i).getCity();
            tblData[i][3] = locations.get(i).getArea();
            tblData[i][4] = locations.get(i).getLocationDesc();
            tblData[i][5] = locations.get(i).getLinkman();
            tblData[i][6] = locations.get(i).getPhoneNumber();
        }
        tblMod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmSelectInfo(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnDCanel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(1000, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnCheck.addActionListener(this);
        this.btnDCanel.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDCanel) {
            this.setVisible(false);
        } else if (e.getSource() == btnCheck) {
            int i = this.dataTable.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "还未选择地址！", "错误", JOptionPane.ERROR_MESSAGE);
            }
            try {
                int id =  new OrdersManage().createOrder(this.locations.get(i));
                FrmSelectCoupon dlg = new FrmSelectCoupon(this,"选择优惠券",true,id);
                dlg.setVisible(true);
                this.setVisible(false);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}