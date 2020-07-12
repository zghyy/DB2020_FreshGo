package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FullDiscountTypeMannage;
import xyz.zghy.freshgo.model.BeanFullDiscountMsg;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/11 下午2:20
 */
public class FrmFullDiscountType extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加满折信息");
    private Button btnDelete = new Button("删除满折信息");
    private Object tblTitle[] = {"满折编号", "内容", "适用商品数量", "折扣", "起始日期", "结束日期"};
    private Object tblData[][];
    List<BeanFullDiscountMsg> fullDiscountMsgs = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);

    void reloadTable() {
        fullDiscountMsgs = new FullDiscountTypeMannage().loadFullDiscountMsg();
        tblData = new Object[fullDiscountMsgs.size()][6];
        for (int i = 0; i < fullDiscountMsgs.size(); i++) {
            tblData[i][0]=fullDiscountMsgs.get(i).getFullDiscountOrder();
            tblData[i][1]=fullDiscountMsgs.get(i).getFullDiscountDesc();
            tblData[i][2]=fullDiscountMsgs.get(i).getFullDiscountNeedCount();
            tblData[i][3]=fullDiscountMsgs.get(i).getFullDiscountData();
            tblData[i][4]=fullDiscountMsgs.get(i).getFullDiscountStartDate();
            tblData[i][5]=fullDiscountMsgs.get(i).getFullDiscountEndDate();
        }
        tblMod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }


    public FrmFullDiscountType(Frame owner, String title, boolean modal) {
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
        if (e.getSource() == this.btnAdd) {
            FrmFullDiscountTypeAdd dlg = new FrmFullDiscountTypeAdd(this,"添加满折信息",true);
            dlg.setVisible(true);
            this.reloadTable();
        } else if ( e.getSource() == this.btnDelete) {
            try {
                int i = this.dataTable.getSelectedRow();
                BeanFullDiscountMsg bfdm = this.fullDiscountMsgs.get(i);
                new FullDiscountTypeMannage().deleteFullDiscountMsg(bfdm);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.reloadTable();
        }
    }
}
