package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.PurchaseManage;
import xyz.zghy.freshgo.model.BeanPurchase;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午1:29
 */
public class FrmPurchaseManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("新增采购");
    private Button btnSpeedUp = new Button("加速送货");
    private Object tblTitle[] = {"采购单编号", "操作管理员姓名", "商品名称", "商品采购数量", "采购订单状态"};
    private Object tblData[][];
    List<BeanPurchase> purchases = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);


    private void reloadTable() {
        purchases = new PurchaseManage().loadPurchase();
        tblData = new Object[purchases.size()][5];
        for (int i = 0; i < purchases.size(); i++) {
            tblData[i][0] = purchases.get(i).getPurchaseId();
            tblData[i][1] = purchases.get(i).getAdminName();
            tblData[i][2] = purchases.get(i).getGoodsName();
            tblData[i][3] = purchases.get(i).getPurchaseNum();
            tblData[i][4] = purchases.get(i).getPurchaseStatus();
        }
        tblMod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }


    public FrmPurchaseManage(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnSpeedUp);
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
        this.btnSpeedUp.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            FrmPurchaseAdd dlg = new FrmPurchaseAdd(this,"新增采购单",true);
            dlg.setVisible(true);
            this.reloadTable();
        } else if (e.getSource() == btnSpeedUp) {
            //TODO
            int i = this.dataTable.getSelectedRow();
            BeanPurchase bp = this.purchases.get(i);
            try {
                new PurchaseManage().speedUp(bp);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.reloadTable();
        }

    }
}
