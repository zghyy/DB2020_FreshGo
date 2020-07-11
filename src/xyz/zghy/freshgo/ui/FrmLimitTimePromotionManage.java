package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.LimitTimePromotionManage;
import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.model.BeanLimitTimePromotion;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午6:59
 */
public class FrmLimitTimePromotionManage extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加限时促销商品");
    private Button btnDelete = new Button("删除限时促销商品");
    private Object tblTitle[] = {"类型序号", "促销商品名称", "促销价格", "促销数量", "起始日期", "结束日期"};
    private Object tblData[][];
    List<BeanLimitTimePromotion> limitTimePromotions = null;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable() {
        limitTimePromotions = new LimitTimePromotionManage().loadLimitTimePromotion();
        tblData = new Object[limitTimePromotions.size()][6];
        for (int i = 0; i < limitTimePromotions.size(); i++) {
            tblData[i][0] = limitTimePromotions.get(i).getLimitTimePromotionOrder();
            tblData[i][1] = limitTimePromotions.get(i).getGoodsName();
            tblData[i][2] = limitTimePromotions.get(i).getLimitTimePromotionPrice();
            tblData[i][3] = limitTimePromotions.get(i).getLimitTimePromotionCount();
            tblData[i][4] = SystemUtil.SDF.format(limitTimePromotions.get(i).getLimitTimePromotionStartTime());
            tblData[i][5] = SystemUtil.SDF.format(limitTimePromotions.get(i).getLimitTimePromotionEndTime());
        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }


    FrmLimitTimePromotionManage(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        this.setSize(800, 600);
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
        if (e.getSource() == this.btnAdd) {
            FrmLimitTimePromotionAdd dlg = new FrmLimitTimePromotionAdd(this, "限时促销商品添加", true);
            dlg.setVisible(true);
            this.reloadTable();

        } else if (e.getSource() == this.btnDelete) {
            int i = this.dataTable.getSelectedRow();
            BeanLimitTimePromotion deleteBLTP = this.limitTimePromotions.get(i);
            try {
                new LimitTimePromotionManage().deleteLimitTimePromotion(deleteBLTP);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.reloadTable();

        }
    }
}
