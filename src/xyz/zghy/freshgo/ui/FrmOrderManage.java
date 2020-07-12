package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.CommentsManage;
import xyz.zghy.freshgo.control.OrdersManage;
import xyz.zghy.freshgo.model.BeanOrder;
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
 * @date 2020/7/11 上午9:13
 */
public class FrmOrderManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnSpeedUp = new Button("加速送货");
    private Button btnBackOrder = new Button("退货");
    private Button btnComment = new Button("评价");
    private Object tblTitle[] = {"订单编号", "原始价格", "实际支付", "订单详细地址", "订单创建时间", "订单状态"};
    private Object tblData[][];
    List<BeanOrder> orders = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);


    private void reloadTable() {
        orders = new OrdersManage().loadOrders();
        tblData = new Object[orders.size()][6];
        for (int i = 0; i < orders.size(); i++) {
            tblData[i][0] = orders.get(i).getoOrder();
            tblData[i][1] = orders.get(i).getOrderOldPrice();
            tblData[i][2] = orders.get(i).getOrderNewPrice();
            tblData[i][3] = orders.get(i).getLocationDetail();
            tblData[i][4] = SystemUtil.SDF.format(orders.get(i).getOrderGettime());
            tblData[i][5] = orders.get(i).getOrderStatus();
        }
        tblMod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmOrderManage(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnSpeedUp);
        toolBar.add(btnBackOrder);
        toolBar.add(btnComment);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        this.setSize(1200, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnSpeedUp.addActionListener(this);
        this.btnBackOrder.addActionListener(this);
        this.btnComment.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSpeedUp) {
            int i = this.dataTable.getSelectedRow();
            try {
                new OrdersManage().speedUp(this.orders.get(i));
                this.reloadTable();
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);

            }
        } else if (e.getSource() == btnBackOrder) {
            int i = this.dataTable.getSelectedRow();
            try {
                new OrdersManage().backOrder(this.orders.get(i));
                this.reloadTable();
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnComment) {
            int i = this.dataTable.getSelectedRow();
            FrmCommentsAdd dlg = new FrmCommentsAdd(this, "添加评论", true, this.orders.get(i));
            dlg.setVisible(true);
            this.reloadTable();

        }

    }
}
