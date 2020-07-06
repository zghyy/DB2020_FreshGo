package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
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
 * @date 2020/7/6 上午11:09
 */
public class FrmGoodsManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加商品");
    private Button btnDelete = new Button("删除商品");
    private Object tblTitle[] = {"商品序号", "类型名称", "商品名称", "商品价格", "会员价格", "商品数量"};
    private Object tblData[][];
    List<BeanGoodsMsg> goodsMsgs = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);

    private void reloadTable() {
        try {
            goodsMsgs = new GoodsManage().loadGoods();
            System.out.println(goodsMsgs.size());
            tblData = new Object[goodsMsgs.size()][6];
            for (int i = 0; i < goodsMsgs.size(); i++) {
                tblData[i][0] = goodsMsgs.get(i).getGoodsOrder();
                tblData[i][1] = goodsMsgs.get(i).getTypeName();
                tblData[i][2] = goodsMsgs.get(i).getGoodsName();
                tblData[i][3] = goodsMsgs.get(i).getGoodsPrice();
                tblData[i][4] = goodsMsgs.get(i).getGoodsVipPrice();
                tblData[i][5] = goodsMsgs.get(i).getGoodsCount();
            }
            tblMod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }


    public FrmGoodsManage(Frame owner, String title, boolean modal) {
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
            FrmGoodsAdd dlg = null;
            try {
                dlg = new FrmGoodsAdd(this, "添加商品", true);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            dlg.setVisible(true);
            this.reloadTable();
        } else if (e.getSource() == btnDelete) {
            try {
                int i = this.dataTable.getSelectedRow();
                BeanGoodsMsg deleteGM = this.goodsMsgs.get(i);
                new GoodsManage().deleteGoods(deleteGM);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.reloadTable();

        }

    }
}
